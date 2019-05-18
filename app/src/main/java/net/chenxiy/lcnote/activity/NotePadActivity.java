package net.chenxiy.lcnote.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.chenxiy.lcnote.MainActivity;
import net.chenxiy.lcnote.R;
import net.chenxiy.lcnote.Repository;
import net.chenxiy.lcnote.net.pojo.NoteData;
import net.chenxiy.lcnote.net.pojo.github.upload.UploadResultInfo;
import net.chenxiy.lcnote.net.pojo.github.upload.request.Committer;
import net.chenxiy.lcnote.net.pojo.github.upload.request.UploadRequest;
import net.chenxiy.lcnote.net.pojo.updatenote.UpDateNoteData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class NotePadActivity extends AppCompatActivity {
    private String Title="";
    EditText editText;
    Uri uri;
    int CAMERA_REQUEST=0;
    private static final String TAG = "NotePadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);
        Intent intent=getIntent();
        Title=intent.getStringExtra("titleSlug");
        editText=findViewById(R.id.quoteTextArea);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportActionBar().setTitle(intent.getStringExtra("title"));
        Log.d(TAG, "onCreate: "+Title);
        Repository.getInstance().getNoteBookData(Title).enqueue(new Callback<NoteData>() {
            @Override
            public void onResponse(Call<NoteData> call, Response<NoteData> response) {
                if(response.body()!=null){
                    editText.setText(response.body().getData().getQuestion().getNote());
                }
            }

            @Override
            public void onFailure(Call<NoteData> call, Throwable t) {

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            CompressAndUploadPhoto task=new CompressAndUploadPhoto();
            task.execute(uri);
            //Log.d(TAG, "onActivityResult: "+encoded);
        }

    }

    class CompressAndUploadPhoto extends AsyncTask<Uri,Void,Void>{

        @Override
        protected Void doInBackground(Uri... uris) {
            ContentResolver cr = getContentResolver();


            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(cr,uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray,Base64.DEFAULT);
            final UploadRequest request=new UploadRequest();
            Committer com=new Committer();
            com.setName("LCNote Android");
            com.setEmail("LCNote@chenxiy.net");
            request.setCommitter(com);
            long time = new Date().getTime();
            request.setMessage("Add "+Title);
            request.setContent(encoded);
            String filepath=Title+"/"+String.valueOf(time)+".png";
            Repository.getInstance().apiService.uploadPhoto("token "+LoginActivity.OauthToken,request, LoginActivity.UserName,LoginActivity.UserRepo,filepath).enqueue(new Callback<UploadResultInfo>() {
                @Override
                public void onResponse(Call<UploadResultInfo> call, Response<UploadResultInfo> response) {
                    if(response.body()!=null){
                        String Url=response.body().getContent().getDownloadUrl();
                        String insert="<img src=\""+Url+"\" width=\"256\" alt=\""+Title+"\">";
                        int start = Math.max(editText.getSelectionStart(), 0);
                        int end = Math.max(editText.getSelectionEnd(), 0);
                        editText.getText().replace(Math.min(start, end), Math.max(start, end),
                                insert, 0,insert.length());
                        Log.d(TAG, "onResponse: "+Url);
                    }
                }

                @Override
                public void onFailure(Call<UploadResultInfo> call, Throwable t) {

                }
            });
            return null;
        }
    }

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }
        public void InsertPic(View view) {
            File tempFile = null;
            try {
                tempFile = createTemporaryFile("camera", ".png");
                tempFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            uri = Uri.fromFile(tempFile);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    public void saveToCloud(final View view) {
        Log.d(TAG, "saveToCloud: ");
        Repository.getInstance().updateNoteBookData(Title,editText.getText().toString()).enqueue(new Callback<UpDateNoteData>() {
            @Override
            public void onResponse(Call<UpDateNoteData> call, Response<UpDateNoteData> response) {
                editText.setText(response.body().getData().getUpdateNote().getQuestion().getNote());

                Toast.makeText(view.getContext(),"Update Success!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UpDateNoteData> call, Throwable t) {

            }
        });

    }
}

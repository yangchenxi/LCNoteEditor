package net.chenxiy.lcnote.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.chenxiy.lcnote.R;
import net.chenxiy.lcnote.Repository;
import net.chenxiy.lcnote.net.pojo.NoteData;
import net.chenxiy.lcnote.net.pojo.updatenote.UpDateNoteData;

import java.io.ByteArrayOutputStream;

public class NotePadActivity extends AppCompatActivity {
    private String Title="";
    EditText editText;
    int CAMERA_REQUEST=0;
    private static final String TAG = "NotePadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pad);
        Intent intent=getIntent();
        Title=intent.getStringExtra("titleSlug");
        editText=findViewById(R.id.quoteTextArea);
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
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            byte[] encoded = Base64.encode(byteArray,Base64.DEFAULT);
            //TODO:Upload To github
            Log.d(TAG, "onActivityResult: "+encoded);
        }

    }
        public void InsertPic(View view) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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

package net.chenxiy.lcnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import net.chenxiy.lcnote.View.ProblemRecyclerViewAdapter;
import net.chenxiy.lcnote.activity.LoginActivity;
import net.chenxiy.lcnote.net.pojo.ProblemData;
import net.chenxiy.lcnote.net.pojo.github.repo.RepoInfo;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivityLog";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView mRecyclerView;
    private SharedPreferences mPreferences;
    ProblemRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = getSharedPreferences(LoginActivity.sharedPrefFile, MODE_PRIVATE);
        if(mPreferences.getString("token","").isEmpty()){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            LoginActivity.OauthToken=mPreferences.getString("token","");
        }
        if(mPreferences.getString("repo","").isEmpty()){
            Repository.getInstance().apiService.getUserRepo("token "+LoginActivity.OauthToken).enqueue(new Callback<List<RepoInfo>>() {
                @Override
                public void onResponse(Call<List<RepoInfo>> call, Response<List<RepoInfo>> response) {

                    if(response.body()!=null) {
                        if(response.body().size()==0) {
                            Toast.makeText(getApplicationContext(), "Please Create A Repo On your Github First!", Toast.LENGTH_LONG).show();
                            return;
                        }


                        final String []items=new String[response.body().size()];
                        for(int i=0;i<response.body().size();i++){
                            items[i]=response.body().get(i).getName();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Choose Repo To Host Photo");

                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                preferencesEditor.putString("repo", items[which]);
                                LoginActivity.UserRepo=items[which];
                                preferencesEditor.apply();
                                Toast.makeText(getApplicationContext(),"All your photos will be hosted on your Repo: "+items[which],Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setCancelable(false);
                        AlertDialog dialog = builder.create();
                        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                        preferencesEditor.putString("name", response.body().get(0).getOwner().getLogin());

                        LoginActivity.UserName=response.body().get(0).getOwner().getLogin();

                        preferencesEditor.apply();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<List<RepoInfo>> call, Throwable t) {

                }
            });
        }else{
            LoginActivity.UserName=mPreferences.getString("name","");
            LoginActivity.UserRepo=mPreferences.getString("repo","");
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        mRecyclerView=findViewById(R.id.problemRecyclerView);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        Toolbar tb=findViewById(R.id.toolbar);
        tb.setTitle("LeetCode Problems");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toc_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ProblemRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);


        SearchView svMovies = findViewById(R.id.svMovies);
        svMovies.setActivated(true);
        svMovies.setQueryHint("Type your keyword here");
        svMovies.onActionViewExpanded();
        svMovies.setIconified(false);
        svMovies.clearFocus();
        svMovies.setFocusable(true);
        svMovies.setIconified(false);
        svMovies.requestFocusFromTouch();
        svMovies.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected: "+item.getTitle());
        switch (itemId) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                break;

        }

        return true;

    }


    @Override
    protected void onStart() {
        super.onStart();
        Repository.getInstance().apiService.getProblemData(CookieManager.getInstance().getCookie("https://leetcode.com"))
                .enqueue(new Callback<ProblemData>() {
                    @Override
                    public void onResponse(Call<ProblemData> call, Response<ProblemData> response) {
                        if(response.body()!=null) {
                            adapter.setData(response.body().getStatStatusPairs());

                            Log.d(TAG, "onResponse: "+response.body().getUserName());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProblemData> call, Throwable t) {
                        Log.e(TAG, "onFailure: ",t );
                    }
                });
    }

    public void Login(View view) {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void cleanCookies(View view) {
        CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean value) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.d(TAG, "onNavigationItemSelected: "+menuItem.getItemId());
        switch (menuItem.getItemId()) {
            case R.id.login:
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);


                break;
            case R.id.about:



                break;
            case R.id.logout:
                CookieManager.getInstance().removeAllCookies(null);
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                preferencesEditor.clear();
                preferencesEditor.apply();
                finish();
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }
}

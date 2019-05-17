package net.chenxiy.lcnote;

import androidx.annotation.NonNull;
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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;



import com.google.android.material.navigation.NavigationView;

import net.chenxiy.lcnote.View.ProblemRecyclerViewAdapter;
import net.chenxiy.lcnote.activity.LoginActivity;
import net.chenxiy.lcnote.net.pojo.ProblemData;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivityLog";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView mRecyclerView;
    ProblemRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }
}

package net.chenxiy.lcnote.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import net.chenxiy.lcnote.MainActivity;
import net.chenxiy.lcnote.R;
import net.chenxiy.lcnote.Repository;
import net.chenxiy.lcnote.net.pojo.github.TokenInfo;

public class LoginActivity extends AppCompatActivity {
    WebView mWebView;
    public static String clientId="d9daf1e6ea82bb4149f6";
    public static String clientSecret="8c698898b46019d8d448a8472e515bcdf720565e";
    private static final String TAG = "LoginActivity";
    private SharedPreferences mPreferences;
    public static String OauthToken="";
    public static String UserRepo="";
    public static String UserName="";
    public static String  sharedPrefFile =
            "net.chenxiy.lcnote.token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mWebView = (WebView) findViewById(R.id.loginWebview);

        WebSettings webSettings = mWebView.getSettings();
        String newUA= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36";
        webSettings.setUserAgentString(newUA);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: "+url);
                if(url.contains("https://leetcode.com/lcnote")){
                    mWebView.loadUrl("https://github.com/login/oauth/authorize?client_id="+clientId+"&redirect_uri=LCNote%3A%2F%2FOauthResp&scope=public_repo&response_type=token&response_mode=form_post");
                    return true;
                }
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                    Uri uri=Uri.parse(url);
                    String code = uri.getQueryParameter("code");
                    Log.d(TAG, "shouldOverrideUrlLoading: Code"+code);

                    Repository.getInstance().apiService.getToken("application/json",clientId,clientSecret,code).enqueue(new Callback<TokenInfo>() {
                        @Override
                        public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                            //save Token To sharedPref
                            if(response.body()!=null) {
                                OauthToken=response.body().getAccessToken();
                                mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                preferencesEditor.putString("token", OauthToken);
                                preferencesEditor.apply();
                            }else {

                                Toast.makeText(getApplicationContext(), "Oauth Fail!", Toast.LENGTH_LONG);
                            }
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity( intent );
                            finish();
                        }

                        @Override
                        public void onFailure(Call<TokenInfo> call, Throwable t) {

                        }
                    });


                return true;
            }
        });
        mWebView.loadUrl("https://leetcode.com/accounts/github/login/?next=/lcnote");
    }


}

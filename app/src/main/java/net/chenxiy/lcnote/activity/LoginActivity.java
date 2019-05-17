package net.chenxiy.lcnote.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.chenxiy.lcnote.MainActivity;
import net.chenxiy.lcnote.R;

public class LoginActivity extends AppCompatActivity {
    WebView mWebView;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mWebView = (WebView) findViewById(R.id.loginWebview);

        WebSettings webSettings = mWebView.getSettings();
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
                    mWebView.loadUrl("https://github.com/login/oauth/authorize?client_id=d9daf1e6ea82bb4149f6&redirect_uri=LCNote%3A%2F%2FOauthResp&scope=public_repo&response_type=token&response_mode=form_post");
                    return true;
                }
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                    Uri uri=Uri.parse(url);
                    String code = uri.getQueryParameter("code");
                    Log.d(TAG, "shouldOverrideUrlLoading: Code"+code);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("code",code);
                    startActivity( intent );

                return true;
            }
        });
        mWebView.loadUrl("https://leetcode.com/accounts/github/login/?next=/lcnote");
    }
}

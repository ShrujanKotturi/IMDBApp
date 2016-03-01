package com.example.shruj.imdbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MovieWebviewActivity extends AppCompatActivity {

    Intent intent;
    String url;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_webview);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(Boolean.TRUE);
        actionBar.setIcon(R.drawable.ic_launcher);

        webView = (WebView) findViewById(R.id.webView);

        if (getIntent().getExtras() != null) {
            intent = getIntent();
            url = Constants.URL_WEB_VIEW + intent.getExtras().getString(Constants.INTENT_IMDB_ID);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            webView.loadUrl(url);
        }


    }
}

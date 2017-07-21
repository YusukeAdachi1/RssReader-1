package com.t_nishikawa.internrssreaderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleViewerActivity extends AppCompatActivity {

    private WebView articleWebView;

    private static final String ArticleUrl= "ARTICLE_URL";

    public static void launchFrom(Activity activity , String url){
        Intent intent = new Intent(activity.getApplicationContext(),ArticleViewerActivity.class);
        intent.putExtra(ArticleUrl , url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_viewer);

        initArticleWebView();

        final Intent receiveIntent = getIntent();
        final String articleUrl = receiveIntent.getStringExtra(ArticleUrl);
        articleWebView.loadUrl(articleUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initArticleWebView() {
        articleWebView = (WebView) findViewById(R.id.article_web_view);
        //リンクをタップしたときに標準ブラウザを起動させない
        articleWebView.setWebViewClient(new WebViewClient());
        //jacascriptを許可する
        articleWebView.getSettings().setJavaScriptEnabled(true);
    }
}

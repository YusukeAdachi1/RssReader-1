package com.t_nishikawa.internrssreaderapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.List;

public class ArticleViewerActivity extends AppCompatActivity {

    private WebView articleWebView;
    private BookMarkDataManager bookMarkDataManager;

    private String articleTitle;
    private String articleUrl;

    private static final String ArticleTitle = "ARTICLE_TITLE";
    private static final String ArticleUrl = "ARTICLE_URL";

    public static void launchFrom(Activity activity, String title, String url) {
        Intent intent = new Intent(activity.getApplicationContext(), ArticleViewerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(ArticleTitle, title);
        intent.putExtra(ArticleUrl, url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_viewer);

        bookMarkDataManager = new DatabaseManager(this.getApplicationContext());

        initArticleWebView();
        initBookMarkButton();

        final Intent receiveIntent = getIntent();
        articleTitle = receiveIntent.getStringExtra(ArticleTitle);
        articleUrl = receiveIntent.getStringExtra(ArticleUrl);

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

    private void initBookMarkButton() {
        FloatingActionButton bookMarkButton = (FloatingActionButton) findViewById(R.id.book_mark_button);
        bookMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookMarkDataManager.BookMarkData bookMarkData = new BookMarkDataManager.BookMarkData(articleTitle, articleUrl);
                bookMarkDataManager.saveBookMark(bookMarkData);
                Toast.makeText(v.getContext(), "ブックマークに追加しました。", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

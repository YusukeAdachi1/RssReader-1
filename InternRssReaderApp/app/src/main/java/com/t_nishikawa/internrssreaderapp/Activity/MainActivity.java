package com.t_nishikawa.internrssreaderapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.t_nishikawa.internrssreaderapp.AsyncWebAccess;
import com.t_nishikawa.internrssreaderapp.R;
import com.t_nishikawa.internrssreaderapp.RssData;
import com.t_nishikawa.internrssreaderapp.RssListAdapter;
import com.t_nishikawa.internrssreaderapp.RssParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static void launchFrom(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    private RssListAdapter rssListAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_bookmark:
                    BookMarkActivity.launchFrom(MainActivity.this);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigationView();
        initListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestRss();
    }

    private void initBottomNavigationView() {
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private void initListView() {
        final RecyclerView rssListView = (RecyclerView) findViewById(R.id.rss_list_view);
        rssListView.setHasFixedSize(true);
        rssListView.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList rssListItems = new ArrayList<>();
        rssListAdapter = new RssListAdapter(rssListItems);
        rssListAdapter.setOnClickListener(new RssListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, RssData rssData) {
                final String title = rssData.title;
                final String url = rssData.url;
                ArticleViewerActivity.launchFrom(MainActivity.this, title, url);
            }
        });
        rssListView.setAdapter(rssListAdapter);
    }

    private void requestRss() {
        final AsyncWebAccess asyncWebAccess = new AsyncWebAccess() {
            @Override
            protected void onPostExecute(String result) {
                RssParser rssParser = new RssParser();
                List<RssData> rssListItems = rssParser.parse(result);
                rssListAdapter.updateList(rssListItems);
            }
        };
        String[] params = {"http://feeds.feedburner.com/hatena/b/hotentry"};
        asyncWebAccess.execute(params);
    }
}

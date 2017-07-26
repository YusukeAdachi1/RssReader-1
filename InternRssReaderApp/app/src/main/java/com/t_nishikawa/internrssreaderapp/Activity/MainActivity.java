package com.t_nishikawa.internrssreaderapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.t_nishikawa.internrssreaderapp.AsyncWebAccess;
import com.t_nishikawa.internrssreaderapp.R;
import com.t_nishikawa.internrssreaderapp.RssListAdapter;
import com.t_nishikawa.internrssreaderapp.RssListItem;
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

    private RssListAdapter adapter;

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

        requestRss();
    }

    private void initBottomNavigationView(){
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private void initListView() {
        final ListView rssListView = (ListView) findViewById(R.id.rss_list_view);

        final ArrayList<RssListItem> rssListItems = new ArrayList<>();
        adapter = new RssListAdapter(this, R.layout.rss_list_item, rssListItems);
        rssListView.setAdapter(adapter);

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ListView listView = (ListView) parent;
                final RssListItem item = (RssListItem) listView.getItemAtPosition(position);
                final String title = item.getTitle();
                final String url = item.getUrl();
                ArticleViewerActivity.launchFrom(MainActivity.this, title, url);
            }
        });
    }

    private void requestRss() {
        final AsyncWebAccess asyncWebAccess = new AsyncWebAccess() {
            @Override
            protected void onPostExecute(String result) {
                RssParser rssParser = new RssParser();
                List<RssListItem> rssListItems = rssParser.parse(result);
                adapter.update(rssListItems);
            }
        };
        String[] params = {"http://feeds.feedburner.com/hatena/b/hotentry"};
        asyncWebAccess.execute(params);
    }
}

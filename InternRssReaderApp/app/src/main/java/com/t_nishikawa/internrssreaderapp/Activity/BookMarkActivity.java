package com.t_nishikawa.internrssreaderapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.t_nishikawa.internrssreaderapp.BookMarkDataManager;
import com.t_nishikawa.internrssreaderapp.BookMarkListAdapter;
import com.t_nishikawa.internrssreaderapp.DatabaseManager;
import com.t_nishikawa.internrssreaderapp.R;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity {

    public static void launchFrom(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), BookMarkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

    private BookMarkListAdapter bookMarkListAdapter;
    private BookMarkDataManager bookMarkDataManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MainActivity.launchFrom(BookMarkActivity.this);
                    return true;
                case R.id.navigation_bookmark:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        initBottomNavigationView();
        initBookMarkList();
    }

    private void initBottomNavigationView() {
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_bookmark);
    }

    private void initBookMarkList() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.book_mark_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<BookMarkDataManager.BookMarkData> list = new ArrayList<>();
        bookMarkListAdapter = new BookMarkListAdapter(list);
        bookMarkListAdapter.setOnClickListener(new BookMarkListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, BookMarkDataManager.BookMarkData bookMarkData) {
                ArticleViewerActivity.launchFrom(BookMarkActivity.this, bookMarkData.id, bookMarkData.title, bookMarkData.url);
            }

            @Override
            public void onLongClick(View view, BookMarkDataManager.BookMarkData bookMarkData) {
                bookMarkDataManager.deleteBookMark(bookMarkData);

                bookMarkDataManager = new DatabaseManager(getApplicationContext());
                List<BookMarkDataManager.BookMarkData> list = bookMarkDataManager.getBookMarkList();
                bookMarkListAdapter.updateList(list);
            }
        });
        mRecyclerView.setAdapter(bookMarkListAdapter);

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        BookMarkDataManager.BookMarkData bookMarkData = bookMarkListAdapter.getItem(viewHolder.getAdapterPosition());
                        bookMarkDataManager.deleteBookMark(bookMarkData);

                        bookMarkDataManager = new DatabaseManager(getApplicationContext());
                        List<BookMarkDataManager.BookMarkData> list = bookMarkDataManager.getBookMarkList();
                        bookMarkListAdapter.updateList(list);
                    }
                });
        swipeToDismissTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bookMarkDataManager = new DatabaseManager(getApplicationContext());
        List<BookMarkDataManager.BookMarkData> list = bookMarkDataManager.getBookMarkList();
        bookMarkListAdapter.updateList(list);
    }
}

package com.t_nishikawa.internrssreaderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper implements BookMarkDataManager {

    private static final int DATABASE_VERSION = 1;
    // Table Names
    private static final String DATABASE_NAME = "RssReaderDatabase";
    // column names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String URL = "url";
    // Table Names
    private static final String TABLE_BOOK_MARK_DETAILS = "BookMarkData";
    private static final String CREATE_TABLE_BOOK_MARK_DETAILS =
            "CREATE TABLE " + TABLE_BOOK_MARK_DETAILS + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TITLE + " TEXT," +
                    URL + " TEXT" +
                    ")";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK_MARK_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }

    @Override
    public void saveBookMark(BookMarkData bookMarkData) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, bookMarkData.title);
        values.put(URL, bookMarkData.url);

        db.insert(TABLE_BOOK_MARK_DETAILS, null, values);
        db.close();
    }

    @Override
    public List<BookMarkData> getBookMarkList() {
        ArrayList bookmarkList = new ArrayList();

        final String selectQuery = String.format("SELECT * FROM %s", TABLE_BOOK_MARK_DETAILS);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                final String title = c.getString(c.getColumnIndex(TITLE));
                final String url = c.getString(c.getColumnIndex(URL));
                if (title == null) {
                    continue;
                }
                if (url == null) {
                    continue;
                }
                BookMarkData bookMarkData = new BookMarkData(title, url);
                bookmarkList.add(bookMarkData);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return bookmarkList;
    }
}

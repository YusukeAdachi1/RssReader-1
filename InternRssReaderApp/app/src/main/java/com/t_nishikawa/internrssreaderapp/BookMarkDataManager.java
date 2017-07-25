package com.t_nishikawa.internrssreaderapp;

import java.util.List;

public interface BookMarkDataManager {

    void saveBookMark(BookMarkData bookMarkData);

    List<BookMarkData> getBookMarkList();


    class BookMarkData{
        final String title;
        final String url;

        BookMarkData(String title , String url){
            this.title  = title;
            this.url = url;
        }

        @Override
        public String toString() {
            return "BookMarkData{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}

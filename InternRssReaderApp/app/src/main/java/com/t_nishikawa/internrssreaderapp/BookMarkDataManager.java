package com.t_nishikawa.internrssreaderapp;

import java.util.List;

public interface BookMarkDataManager {

    void saveBookMark(BookMarkData bookMarkData);
    void deleteBookMark(BookMarkData bookMarkData);

    List<BookMarkData> getBookMarkList();


    class BookMarkData{
        public final String id;
        public final String title;
        public final String url;

        public BookMarkData(String id , String title, String url){
            this.id  = id;
            this.title  = title;
            this.url = url;
        }

        @Override
        public String toString() {
            return "BookMarkData{" +
                    "id='" + id + '\'' +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}

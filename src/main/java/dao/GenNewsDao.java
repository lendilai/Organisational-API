package dao;

import models.GenNews;

import java.util.List;

public interface GenNewsDao {
    //Add general news
    void add(GenNews genNews);

    //Find general news
    GenNews findById(int id);

    //Get all general news
    List<GenNews> getAllGenNews();
//
////    Update the news
//    void updateNews(int id, String title, String content, String importance);
}

package dao;

import models.DepNews;

import java.util.List;

public interface DepNewsDao {
    //Add new for a department
    void add(DepNews depNews);

    //Find news by the id
    DepNews findById(int id);

    //Update department news
    void updateDepNews(int id, String title, String content, String importance);

    //Get all department news by department id
    List<DepNews> getAllDepNews(int departmentId);
}

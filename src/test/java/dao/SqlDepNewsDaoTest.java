package dao;

import models.DepNews;
import models.Departments;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SqlDepNewsDaoTest {

    private static Connection conn;
    private static SqlDepNewsDao sqlDepNewsDao;
    private static SqlDepartmentsDao sqlDepartmentsDao;

    public DepNews setUpNews(){
        return new DepNews("Holiday", "The school will be closed of Thursday", "Very important", 2);
    }

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/api_dev_test";
        Sql2o sql2o = new Sql2o(connectionString, "rlgriff", "547");
        sqlDepNewsDao = new SqlDepNewsDao(sql2o);
        sqlDepartmentsDao = new SqlDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        sqlDepNewsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addsDepNewsAndSetsId() throws Exception{
        DepNews news = setUpNews();
        sqlDepNewsDao.add(news);
        int theId = news.getId();
        assertEquals(theId, news.getId());
    }

    @Test
    public void findsNewsById() {
        DepNews news = setUpNews();
        sqlDepNewsDao.add(news);
        DepNews found = sqlDepNewsDao.findById(news.getId());
        assertEquals(news, found);
    }

    @Test
    public void updatesDepartmentNewsCorrectly() throws Exception{
        DepNews depNews = setUpNews();
        sqlDepNewsDao.add(depNews);
        sqlDepNewsDao.updateDepNews(depNews.getId(), "No-holiday", "Noooooo", "Mild importance");
        DepNews found = sqlDepNewsDao.findById(depNews.getId());
        assertEquals("Noooooo", found.getContent());
    }

    @Test
    public void getsAllNewsByDepartment() {
        Departments first = new Departments("Tech", "ydj", 23);
        sqlDepartmentsDao.add(first);
        DepNews news = new DepNews("Holiday", "The school will be closed of Thursday", "Very important", first.getId());
        DepNews nextNews = new DepNews("Holiday", "The school will be closed of Thursday", "Very important", first.getId());
        sqlDepNewsDao.add(news);
        sqlDepNewsDao.add(nextNews);
        assertEquals(2, sqlDepNewsDao.getAllDepNews(first.getId()).size());
    }
}
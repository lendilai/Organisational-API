package dao;

import com.sun.tools.javac.jvm.Gen;
import models.GenNews;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SqlGenNewsDaoTest {

    private static Connection conn;
    private static SqlGenNewsDao sqlGenNewsDao;

    public GenNews setUpGenNews(){
        return new GenNews("Holiday", "The school will not be open on Thursday", "Very important");
    }

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/api_dev_test";
        Sql2o sql2o = new Sql2o(connectionString, "rlgriff", "547");
        sqlGenNewsDao = new SqlGenNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        sqlGenNewsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addsGenNewsAndSetsId() throws Exception{
        GenNews news = setUpGenNews();
        GenNews nextNews = setUpGenNews();
        sqlGenNewsDao.add(news);
        sqlGenNewsDao.add(nextNews);
        int theId = nextNews.getId();
        assertEquals(theId, nextNews.getId());
    }

    @Test
    public void getsAllGeneralNewsAdded() {
        GenNews news = setUpGenNews();
        GenNews nextNews = setUpGenNews();
        sqlGenNewsDao.add(news);
        sqlGenNewsDao.add(nextNews);
        assertEquals(2, sqlGenNewsDao.getAllGenNews().size());
    }

    @Test
    public void findsById() {
        GenNews news = setUpGenNews();
        GenNews nextNews = setUpGenNews();
        sqlGenNewsDao.add(news);
        sqlGenNewsDao.add(nextNews);
        int theId = nextNews.getId();
        assertEquals(nextNews, sqlGenNewsDao.findById(theId));
    }

    @Test
    public void updatesGeneralNewsInAllFields() throws Exception{
        GenNews nextNews = setUpGenNews();
        sqlGenNewsDao.add(nextNews);
        sqlGenNewsDao.updateNews(nextNews.getId(), "No-Holiday", "The holiday on Thursday has been nullified", "Very important");
        GenNews found = sqlGenNewsDao.findById(nextNews.getId());
        assertEquals("No-Holiday", found.getTitle());
    }
}
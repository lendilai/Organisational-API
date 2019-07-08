package dao;

import com.sun.tools.javac.jvm.Gen;
import models.GenNews;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SqlGenNewsDaoTest {

    private Connection conn;
    private SqlGenNewsDao sqlGenNewsDao;

    public GenNews setUpGenNews(){
        return new GenNews("Holiday", "The school will not be open on Thursday", "Very important");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sqlGenNewsDao = new SqlGenNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        sqlGenNewsDao.clearAll();
        conn.close();
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
}
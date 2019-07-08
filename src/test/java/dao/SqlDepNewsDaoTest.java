package dao;

import models.DepNews;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SqlDepNewsDaoTest {

    private Connection conn;
    private SqlDepNewsDao sqlDepNewsDao;

    public DepNews setUpNews(){
        return new DepNews("Holiday", "The school will be closed of Thursday", "Very important", 2);
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sqlDepNewsDao = new SqlDepNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addsDepNewsAndSetsId() throws Exception{
        DepNews news = setUpNews();
        sqlDepNewsDao.add(news);
        int theId = news.getId();
        assertEquals(theId, news.getId());
    }
}
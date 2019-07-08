package dao;

import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SqlUserDaoTest {
    private Connection conn;
    private SqlUserDao sqlUserDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sqlUserDao = new SqlUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Users setUpUser(){
        return new Users("John Doe", "CTO", 3);
    }

    @Test
    public void addsUserAndSetsId() throws Exception{
        Users newUser = setUpUser();
        sqlUserDao.add(newUser);
        int theId = newUser.getId();
        assertEquals(theId, newUser.getId());
    }
}
package dao;

import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SqlUserDaoTest {
    private Connection conn;
    private SqlUserDao sqlUserDao;
    private SqlDepartmentsDao sqlDepartmentsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sqlUserDao = new SqlUserDao(sql2o);
        sqlDepartmentsDao = new SqlDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Users setUpUser(){
        return new Users("John Doe", "CTO", "http://duxhg.com");
    }

    @Test
    public void addsUserAndSetsId() throws Exception{
        Users newUser = setUpUser();
        sqlUserDao.add(newUser);
        int theId = newUser.getId();
        assertEquals(theId, newUser.getId());
    }

    @Test
    public void getsAUserById() {
        Users newUser = setUpUser();
        sqlUserDao.add(newUser);
        Users found = sqlUserDao.findById(newUser.getId());
        assertEquals(newUser, found);
    }

    @Test
    public void getsAllUsersAdded() {
        Users newUser = setUpUser();
        Users second = setUpUser();
        sqlUserDao.add(newUser);
        sqlUserDao.add(second);
        assertEquals(2, sqlUserDao.getAllUsers().size());
    }

    @Test
    public void retrievesCorrectDepartmentsForAUser() throws Exception{
        Users firstUser = setUpUser();
        sqlUserDao.add(firstUser);
        Departments first = new Departments("Marketing", "Sell products", 45);
        Departments second = new Departments("Sales", "Too much work", 21);
        sqlDepartmentsDao.add(first);
        sqlDepartmentsDao.add(second);
        sqlUserDao.addUserToDepartment(firstUser, first);
        sqlUserDao.addUserToDepartment(firstUser, second);
        Departments[] departments = {first, second};
        assertEquals(Arrays.asList(departments), sqlUserDao.getAllDepartmentsForAUser(firstUser.getId()));
    }
}
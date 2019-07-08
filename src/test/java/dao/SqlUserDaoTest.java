package dao;

import models.Departments;
import models.Users;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SqlUserDaoTest {
    private static Connection conn;
    private static SqlUserDao sqlUserDao;
    private static SqlDepartmentsDao sqlDepartmentsDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/api_dev_test";
        Sql2o sql2o = new Sql2o(connectionString, "rlgriff", "547");
        sqlUserDao = new SqlUserDao(sql2o);
        sqlDepartmentsDao = new SqlDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        sqlUserDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed");
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
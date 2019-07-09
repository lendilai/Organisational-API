package dao;

import models.DepNews;
import models.Departments;
import models.Users;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SqlDepartmentsDaoTest {
    private static Connection conn;
    private static SqlDepartmentsDao sqlDepartmentsDao;
    private static SqlDepNewsDao sqlDepNewsDao;
    private static SqlUserDao sqlUserDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/api_dev_test";
        Sql2o sql2o = new Sql2o(connectionString, "rlgriff", "547");
        sqlDepNewsDao = new SqlDepNewsDao(sql2o);
        sqlDepartmentsDao = new SqlDepartmentsDao(sql2o);
        sqlUserDao = new SqlUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception{
        System.out.println("Clearing Database");
        sqlDepartmentsDao.clearAll();
        sqlDepNewsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("Connection closed");
    }

    public Departments setUpDep(){
        return new Departments("Marketing", "Gain investors", 16);
    }

    @Test
    public void addsDepartmentsToTheDBAndSetsId() throws Exception{
        Departments newDep = setUpDep();
        sqlDepartmentsDao.add(newDep);
        int theId = newDep.getId();
        assertEquals(theId, newDep.getId());
    }

    @Test
    public void findsDepartmentById() {
        Departments newDep = setUpDep();
        Departments second = setUpDep();
        sqlDepartmentsDao.add(newDep);
        sqlDepartmentsDao.add(second);
        Departments found = sqlDepartmentsDao.findById(newDep.getId());
        assertEquals(found.getDescription(), newDep.getDescription());
    }

    @Test
    public void getsAllDepartments() {
        Departments newDep = setUpDep();
        Departments second = setUpDep();
        sqlDepartmentsDao.add(newDep);
        sqlDepartmentsDao.add(second);
        assertEquals(2, sqlDepartmentsDao.getAll().size());
    }

    @Test
    public void getsAllNewsForADepartment() {
        Departments first = setUpDep();
        DepNews news = new DepNews("Holiday", "Holiday on Thursday", "Very important", first.getId());
        DepNews nextNews = new DepNews("Nothing", "Holiday on Thursday", "Very important", first.getId());
        sqlDepNewsDao.add(news);
        sqlDepNewsDao.add(nextNews);
        assertEquals(2, sqlDepartmentsDao.getAllDepartmentNews(first.getId()).size());
        assertEquals("Nothing", sqlDepartmentsDao.getAllDepartmentNews(first.getId()).get(1).getTitle());
    }

    @Test
    public void getsAllUsersInADepartment() throws Exception{
        Users first = new Users("John Doe", "Intern", "http://duxhg.com");
        Users second = new Users("Lendilai", "Owner", "http://duxhg.com");
        sqlUserDao.add(first);
        sqlUserDao.add(second);
        Departments newDepartment = setUpDep();
        sqlDepartmentsDao.add(newDepartment);
        sqlDepartmentsDao.addDepartmentToUser(newDepartment, first);
        sqlDepartmentsDao.addDepartmentToUser(newDepartment, second);
        Users[] users = {first, second};
        assertEquals(Arrays.asList(users), sqlDepartmentsDao.getAllUsersForADepartment(newDepartment.getId()));
    }
}
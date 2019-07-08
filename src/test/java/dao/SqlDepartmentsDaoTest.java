package dao;

import models.DepNews;
import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SqlDepartmentsDaoTest {
    private Connection conn;
    private SqlDepartmentsDao sqlDepartmentsDao;
    private SqlDepNewsDao sqlDepNewsDao;
    private SqlUserDao sqlUserDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        sqlDepNewsDao = new SqlDepNewsDao(sql2o);
        sqlDepartmentsDao = new SqlDepartmentsDao(sql2o);
        sqlUserDao = new SqlUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        sqlDepartmentsDao.clearAll();
        conn.close();
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
        Users first = new Users("John Doe", "Intern");
        Users second = new Users("Lendilai", "Owner");
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
package dao;

import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SqlDepartmentsDaoTest {
    private Connection conn;
    private SqlDepartmentsDao sqlDepartmentsDao;
    private SqlUserDao sqlUserDao;

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
    public void getsUserByDepartment() {
        Users newUser = new Users("John Doe", "CTO", 3);
        sqlUserDao.add(newUser);
        Departments newDepartment = setUpDep();
        sqlDepartmentsDao.add(newDepartment);
        assertEquals(newUser, sqlDepartmentsDao.getAllUsersByDepartment(3).get(0));
    }
}
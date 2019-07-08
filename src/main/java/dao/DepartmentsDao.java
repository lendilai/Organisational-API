package dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface DepartmentsDao {
    //Add department
    void add(Departments departments);

    //Find by id
    Departments findById(int id);

    //Get all departments
    List<Departments> getAll();

}

package dao;

import models.DepNews;
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

    //Get all news for a department
    List<DepNews> getAllDepartmentNews(int departmentId);

    //Add a department to a user
    void addDepartmentToUser(Departments department, Users user);

    //Get all users in a particular department
    List<Users> getAllUsersForADepartment(int department_id);
}

package dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface UserDao {
    //Add a user
    void add(Users user);

    //Find a user by the id
    Users findById(int id);

    //Get all users
    List<Users> getAllUsers();

    //Add a user to a department
    void addUserToDepartment(Users user, Departments department);

    //Get all departments a user belongs to
    List<Departments> getAllDepartmentsForAUser(int user_id);

}

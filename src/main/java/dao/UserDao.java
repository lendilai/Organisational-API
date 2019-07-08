package dao;

import models.Users;

import java.util.List;

public interface UserDao {
    //Add a user
    void add(Users user);

    //Find a user by the id
    Users findById(int id);

    //Get all users
    List<Users> getAllUsers();
}

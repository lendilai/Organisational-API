import dao.SqlDepNewsDao;
import dao.SqlDepartmentsDao;
import dao.SqlGenNewsDao;
import dao.SqlUserDao;
import models.DepNews;
import models.Departments;
import models.GenNews;
import models.Users;
import org.sql2o.Sql2o;
import com.google.gson.Gson;
import spark.Request;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        SqlDepartmentsDao departmentsDao;
        SqlDepNewsDao depNewsDao;
        SqlGenNewsDao genNewsDao;
        SqlUserDao userDao;

        String connectIt = "jdbc:postgresql://localhost:5432/api_dev";
        Sql2o sql2o = new Sql2o(connectIt, "rlgriff", "547");

        departmentsDao = new SqlDepartmentsDao(sql2o);
        depNewsDao = new SqlDepNewsDao(sql2o);
        genNewsDao = new SqlGenNewsDao(sql2o);
        userDao = new SqlUserDao(sql2o);
        Gson gson = new Gson();

        //post: Add general news
        post("/generalNews/new", "application/json", (request, response) -> {
            GenNews genNews = gson.fromJson(request.body(), GenNews.class);
            genNewsDao.add(genNews);
            response.status(201);
            return gson.toJson(genNews);
        });

        //Get: View all general news
        get("/generalNews", "application/json", (request, response) -> {
            return gson.toJson(genNewsDao.getAllGenNews());
        });

        //post: Add a department
        post("/departments/new", "application/json", (request, response) -> {
            Departments departments = gson.fromJson(request.body(), Departments.class);
            departmentsDao.add(departments);
            response.status(201);
            return gson.toJson(departments);
        });

        //Get: View all departments
        get("/departments", "application/json", (request, response) -> {
            return gson.toJson(departmentsDao.getAll());
        });

        //post: Add news to a department
        post("/departments/:id/depNews/new", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            DepNews depNews = gson.fromJson(request.body(), DepNews.class);
            depNews.setId(id);
            depNewsDao.add(depNews);
            response.status(201);
            return gson.toJson(depNews);
        });

        //Get: View all news for a department
        get("/departments/:id/depNews", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            return gson.toJson(depNewsDao.getAllDepNews(id));
        });

        //post: Add a user to a department
        post("/departments/:departmentId/users/:userId", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            int userId = Integer.parseInt(request.params("userId"));
            Departments department = departmentsDao.findById(departmentId);
            Users user = userDao.findById(userId);

            if(department != null && user != null){
                userDao.addUserToDepartment(user, department);
                response.status(201);
                return gson.toJson(String.format("User '%s' and department '%s' can be associated", user.getName(), department.getName()));
            }else{
                throw new Exception("The two cannot be associated");
            }

        });

        //Get: View all users in a department
        get("/departments/:departmentId/users", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            Departments found = departmentsDao.findById(departmentId);

            if(found == null){
                throw new Exception("Sorry, no department with that id exists");
            }
            else if(departmentsDao.getAllUsersForADepartment(departmentId).size() == 0){
                return "{\"message\":\"This department does not have any users\"}";
            }
            else{
                return gson.toJson(departmentsDao.getAllUsersForADepartment(departmentId));
            }
        });

        //post: Add a user
        post("/users/new", "application/json", (request, response) -> {
            Users user = gson.fromJson(request.body(), Users.class);
            userDao.add(user);
            response.status(201);
            return gson.toJson(user);
        });

        //Get: View all users
        get("/users", "application/json", (request, response) -> {
            return gson.toJson(userDao.getAllUsers());
        });

        //post: Add a department to a user
        post("users/:userId/departments/:departmentId", "application/json", (request, response) -> {
           int userId = Integer.parseInt(request.params("userId"));
           int departmentId = Integer.parseInt(request.params("departmentId"));
           Users foundUser = userDao.findById(userId);
           Departments foundDep = departmentsDao.findById(departmentId);

           if(foundDep != null && foundUser != null){
               departmentsDao.addDepartmentToUser(foundDep, foundUser);
               response.status(201);
               return gson.toJson("The user and the department have been associated");
           }
           else{
               throw new Exception("The user and department cannot be associated");
           }

        });

        //Get: View all departments a user belongs to
        get("users/:userId/departments", "application/json", (request, response) -> {
            int userId = Integer.parseInt(request.params("userId"));
            Users foundUser = userDao.findById(userId);

            if(foundUser ==  null){
                throw new Exception("No user with that id exists");
            }
            else if(userDao.getAllDepartmentsForAUser(userId).size() == 0){
                return "{\"message\":\"This user is not associated with any department\"}";
            }
            else{
                return gson.toJson(userDao.getAllDepartmentsForAUser(userId));
            }
        });

        //Filter for the response type
        after((request, response) -> {
           response.type("application/json");
        });
    }
}
//Java hautaniweza

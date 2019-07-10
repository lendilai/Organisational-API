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
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
public class App {
    public static void main(String[] args) {
//        ProcessBuilder process = new ProcessBuilder();
//        Integer port;
//
//        if (process.environment().get("PORT") != null) {
//            port = Integer.parseInt(process.environment().get("PORT"));
//        }else {
//            port = 4567;
//        }
//        port(port);

        staticFileLocation("/public");

        SqlDepartmentsDao departmentsDao;
        SqlDepNewsDao depNewsDao;
        SqlGenNewsDao genNewsDao;
        SqlUserDao userDao;

        String connectIt = "jdbc:postgresql://localhost:5432/api_dev";
        Sql2o sql2o = new Sql2o(connectIt, "rlgriff", "547");
//        String connectionString = "jdbc:postgresql://ec2-107-22-211-248.compute-1.amazonaws.com:5432/dfdutmjkvs127d";
//        Sql2o sql2o = new Sql2o(connectionString, "gwpuliyjmrbmrp", "c35fb50574fbe545785a3a02849d27217ff062ef9642a55c0677d7ba6d42fb90");

        departmentsDao = new SqlDepartmentsDao(sql2o);
        depNewsDao = new SqlDepNewsDao(sql2o);
        genNewsDao = new SqlGenNewsDao(sql2o);
        userDao = new SqlUserDao(sql2o);
        Gson gson = new Gson();

        //Templates routing
        get("/", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allGenNews", genNewsDao.getAllGenNews());
            return new ModelAndView(user, "home.hbs");
        }, new HandlebarsTemplateEngine());

        get("/genNews", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allGenNews", genNewsDao.getAllGenNews());
            return new ModelAndView(user, "GenNews.hbs");
        }, new HandlebarsTemplateEngine());

        post("genNews/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            String title = request.queryParams("title");
            String content = request.queryParams("content");
            String importance = request.queryParams("importance");
            GenNews userNews = new GenNews(title, content, importance);
            genNewsDao.add(userNews);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/Departments", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allDeps", departmentsDao.getAll());
            return new ModelAndView(user, "departments.hbs");
        }, new HandlebarsTemplateEngine());

        post("/Departments/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            String name = request.queryParams("department");
            String description = request.queryParams("desc");
            int userNo = Integer.parseInt(request.queryParams("number"));
            Departments newDep = new Departments(name, description, userNo);
            departmentsDao.add(newDep);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine());


        get("/Users", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allUsers", userDao.getAllUsers());
            return new ModelAndView(user, "users.hbs");
        }, new HandlebarsTemplateEngine());

        post("Users/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            String employee = request.queryParams("employee");
            String position = request.queryParams("position");
            String url = request.queryParams("url");
            Users newUser = new Users(employee, position, url);
            userDao.add(newUser);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //View department details
        get("/Departments/:id", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            Departments found = departmentsDao.findById(theId);
            user.put("department-data", found);
            return new ModelAndView(user, "Dep-details.hbs");
        }, new HandlebarsTemplateEngine());

        //View dep news
        get("/Departments/:id/DepNews", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            Departments found = departmentsDao.findById(theId);
            user.put("allDepNews", departmentsDao.getAllDepartmentNews(theId));
            user.put("depInfo", found);
            return new ModelAndView(user, "Dep-News.hbs");
        }, new HandlebarsTemplateEngine());

        //post dep news
        post("/Departments/:id/DepNews/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            String title = request.queryParams("title");
            String content = request.queryParams("content");
            String importance = request.queryParams("importance");
            DepNews newNews = new DepNews(title, content, importance, theId);
            depNewsDao.add(newNews);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //View department users
        get("/Departments/:depId/users", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int depId = Integer.parseInt(request.params("depId"));
            user.put("depUsers", departmentsDao.getAllUsersForADepartment(depId));
            Departments found = departmentsDao.findById(depId);
            user.put("depId", found);
            user.put("allUsers", userDao.getAllUsers());
            return new ModelAndView(user, "Dep-Users.hbs");
        }, new HandlebarsTemplateEngine());


        //add department to user while in departments
        post("/Departments/:depId/users/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int depId = Integer.parseInt(request.params("depId"));
            int userId = Integer.parseInt(request.queryParams("userId"));
            Users foundUser = userDao.findById(userId);
            Departments foundDep = departmentsDao.findById(depId);
            departmentsDao.addDepartmentToUser(foundDep, foundUser);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine());
        //view user details
        //add user to department while in users










        //Json routing

        //post: Add general news
        post("/generalNews/new", "application/json", (request, response) -> {
            GenNews genNews = gson.fromJson(request.body(), GenNews.class);
            genNewsDao.add(genNews);
            response.status(201);
            response.type("application/json");
            return gson.toJson(genNews);
        });

        //Get: View all general news
        get("/generalNews", "application/json", (request, response) -> {
            response.type("application/json");
            return gson.toJson(genNewsDao.getAllGenNews());
        });

        //post: Add a department
        post("/departments/new", "application/json", (request, response) -> {
            Departments departments = gson.fromJson(request.body(), Departments.class);
            departmentsDao.add(departments);
            response.status(201);
            response.type("application/json");
            return gson.toJson(departments);
        });

        //Get: View all departments
        get("/departments", "application/json", (request, response) -> {
            response.type("application/json");
            return gson.toJson(departmentsDao.getAll());
        });

        //post: Add news to a department
        post("/departments/:id/depNews/new", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            DepNews depNews = gson.fromJson(request.body(), DepNews.class);
            depNews.setId(id);
            depNewsDao.add(depNews);
            response.status(201);
            response.type("application/json");
            return gson.toJson(depNews);
        });

        //Get: View all news for a department
        get("/departments/:id/depNews", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            response.type("application/json");
            return gson.toJson(depNewsDao.getAllDepNews(id));
        });

        //post: Add a user to a department
        post("/departments/:departmentId/users/:userId", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            int userId = Integer.parseInt(request.params("userId"));
            Departments department = departmentsDao.findById(departmentId);
            Users user = userDao.findById(userId);
            response.type("application/json");
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
            response.type("application/json");
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
            response.type("application/json");
            return gson.toJson(user);
        });

        //Get: View all users
        get("/users", "application/json", (request, response) -> {
            response.type("application/json");
            return gson.toJson(userDao.getAllUsers());
        });

        //post: Add a department to a user
        post("users/:userId/departments/:departmentId", "application/json", (request, response) -> {
           int userId = Integer.parseInt(request.params("userId"));
           int departmentId = Integer.parseInt(request.params("departmentId"));
           Users foundUser = userDao.findById(userId);
           Departments foundDep = departmentsDao.findById(departmentId);
            response.type("application/json");
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
            response.type("application/json");
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

//        //Filter for the response type
//        after((request, response) -> {
//           response.type("application/json");
//        });
    }
}
//Java hautaniweza

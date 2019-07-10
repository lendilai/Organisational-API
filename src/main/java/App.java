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
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        }else {
            port = 4567;
        }
        port(port);

        staticFileLocation("/public");

        SqlDepartmentsDao departmentsDao;
        SqlDepNewsDao depNewsDao;
        SqlGenNewsDao genNewsDao;
        SqlUserDao userDao;

//        String connectIt = "jdbc:postgresql://localhost:5432/api_dev";
//        Sql2o sql2o = new Sql2o(connectIt, "rlgriff", "547");
        String connectionString = "jdbc:postgresql://ec2-107-21-120-104.compute-1.amazonaws.com:5432/d5hummo8u96blt";
        Sql2o sql2o = new Sql2o(connectionString, "mjkpdmebavlbpr", "1c3c9da429519806d8b9199ad9580453827bbb3d29d0deb508b47e938e67de45");

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
        }, new HandlebarsTemplateEngine()); //passed

        get("/genNews", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allGenNews", genNewsDao.getAllGenNews());
            return new ModelAndView(user, "GenNews.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        post("genNews/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            String title = request.queryParams("title");
            String content = request.queryParams("content");
            String importance = request.queryParams("importance");
            GenNews userNews = new GenNews(title, content, importance);
            genNewsDao.add(userNews);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        get("/Departments", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allDeps", departmentsDao.getAll());
            return new ModelAndView(user, "departments.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        post("/Departments/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            String name = request.queryParams("department");
            String description = request.queryParams("desc");
            int userNo = Integer.parseInt(request.queryParams("number"));
            Departments newDep = new Departments(name, description, userNo);
            departmentsDao.add(newDep);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine()); //passed


        get("/Users", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            user.put("allUsers", userDao.getAllUsers());
            return new ModelAndView(user, "users.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        post("Users/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            String employee = request.queryParams("employee");
            String position = request.queryParams("position");
            String url = request.queryParams("url");
            Users newUser = new Users(employee, position, url);
            userDao.add(newUser);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine());//passed

        //View department details
        get("/Departments/:id", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            Departments found = departmentsDao.findById(theId);
            user.put("department-data", theId);
            user.put("number", found.getUser_no());
            return new ModelAndView(user, "Dep-details.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        //View dep news
        get("/Departments/:id/DepNews", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            Departments found = departmentsDao.findById(theId);
            user.put("allDepNews", departmentsDao.getAllDepartmentNews(theId));
            user.put("depInfo", theId);
            return new ModelAndView(user, "Dep-News.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        //post dep news
        post("/Departments/:id/DepNews/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            String title = request.queryParams("title");
            String content = request.queryParams("content");
            String importance = request.queryParams("importance");
            DepNews newNews = new DepNews(title, content, importance, theId);
            depNewsDao.add(newNews);
            System.out.println(depNewsDao.getAllDepNews(theId));
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        //View department users
        get("/Departments/:depId/users", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int depId = Integer.parseInt(request.params("depId"));
            Departments putId = departmentsDao.findById(depId);
            user.put("depUsers", departmentsDao.getAllUsersForADepartment(depId));
            user.put("depId", depId);
            user.put("theRealId", putId.getId());
            user.put("allUsers", userDao.getAllUsers());
            return new ModelAndView(user, "dep-users.hbs");
        }, new HandlebarsTemplateEngine()); //passed


        //add department to user while in departments
        post("/Departments/:depId/users/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int depId = Integer.parseInt(request.params("depId"));
            int userId = Integer.parseInt(request.queryParams("userId"));
            Users foundUser = userDao.findById(userId);
            Departments foundDep = departmentsDao.findById(depId);
            if(foundDep.getUser_no() <= departmentsDao.getAllUsersForADepartment(depId).size() ||
                    foundDep.getMAX_USER_NO_PER_DEP() <= departmentsDao.getAllUsersForADepartment(depId).size()){
                String full = "Sorry, you cannot add any more employees to this department";
                user.put("full", full);
            }
            else {
                departmentsDao.addDepartmentToUser(foundDep, foundUser);
            }
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine()); //passed

        //view user details
        get("/Users/:id", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int theId = Integer.parseInt(request.params("id"));
            Users foundUser = userDao.findById(theId);
            user.put("theId", foundUser.getId());
            user.put("userDetails", foundUser);
            user.put("userDeps", userDao.getAllDepartmentsForAUser(theId));
            user.put("allDeps", departmentsDao.getAll());
            return new ModelAndView(user, "user-details.hbs");
        }, new HandlebarsTemplateEngine()); //!!Not displaying


        //add user to department while in users
        post("/Users/:userId/departments/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int userId = Integer.parseInt(request.params("userId"));
            int depId = Integer.parseInt(request.queryParams("departmentId"));
            Users foundUser = userDao.findById(userId);
            Departments foundDep = departmentsDao.findById(depId);
            userDao.addUserToDepartment(foundUser, foundDep);
            return new ModelAndView(user, "success.hbs");
        }, new HandlebarsTemplateEngine()); //passed





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

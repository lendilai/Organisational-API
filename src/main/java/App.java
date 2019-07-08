import dao.SqlDepNewsDao;
import dao.SqlDepartmentsDao;
import dao.SqlGenNewsDao;
import dao.SqlUserDao;
import models.Departments;
import models.GenNews;
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
        //Get: View all departments

        //Get: View all news for a department
        //post: Add news to a department
        //Get: View all users in a department
        //post: Add a user to a department
        //Get: View all users
        //post: Add a user
        //post: Add a department to a user
        //Get: View all departments a user belongs to

        //Filter for the response type
        after((request, response) -> {
           response.type("application/json");
        });
    }
}

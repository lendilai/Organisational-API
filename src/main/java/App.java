import dao.SqlDepNewsDao;
import dao.SqlDepartmentsDao;
import dao.SqlGenNewsDao;
import dao.SqlUserDao;
import models.Departments;
import org.sql2o.Sql2o;
import com.google.gson.Gson;
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

        post("/departments/new", "application/json", (request, response) -> {
            Departments departments = gson.fromJson(request.body(), Departments.class); //Make a java object from json using Gson
            departmentsDao.add(departments);
            response.status(201); //Success: request received and resource created
            return gson.toJson(departments);
        });

        after((request, response) -> {
            response.type("application/json");
        });
    }
}

package dao;

import models.Departments;
import models.Users;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class SqlUserDao implements UserDao {

    private final Sql2o sql2o;

    public SqlUserDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users user){
        String sql = "INSERT INTO users(name, position, url) VALUES (:name, :position, :url)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Users> getAllUsers(){
        String sql = "SELECT * FROM users";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Users.class);
        }
    }

    @Override
    public Users findById(int id){
        String sql = "SELECT * FROM users WHERE id = :id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Users.class);
        }
    }

    @Override
    public void addUserToDepartment(Users user, Departments department){
        String sql = "INSERT INTO departments_users(department_id, user_id) VALUES (:department_id, :user_id)";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("department_id", department.getId())
                    .addParameter("user_id", user.getId())
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Departments> getAllDepartmentsForAUser(int user_id){
        ArrayList<Departments> allDepartments = new ArrayList<>();
        String matchToGetDepartmentId = "SELECT department_id FROM departments_users WHERE user_id =:user_id";
        try(Connection conn = sql2o.open()){
            List<Integer> allDepartmentIds = conn.createQuery(matchToGetDepartmentId).addParameter("user_id", user_id)
                    .executeAndFetch(Integer.class);
            for(Integer department_id : allDepartmentIds){
                String getFromDepartments = "SELECT * FROM departments WHERE id =:department_id";
                allDepartments.add(conn.createQuery(getFromDepartments).addParameter("department_id", department_id).executeAndFetchFirst(Departments.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allDepartments;
    }

}

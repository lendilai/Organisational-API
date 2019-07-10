package dao;
import models.DepNews;
import models.Users;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import models.Departments;

import java.util.ArrayList;
import java.util.List;

public class SqlDepartmentsDao implements DepartmentsDao {
    private final Sql2o sql2o;

    public SqlDepartmentsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Departments departments){
        String sql = "INSERT INTO departments (name, description, user_no) VALUES (:name, :description, :user_no)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(departments)
                    .executeUpdate()
                    .getKey();
            departments.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Departments findById(int id){
        String sql = "SELECT * FROM departments WHERE id =:id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
        }
    }

    @Override
    public List<Departments> getAll(){
        String sql = "SELECT * FROM departments ORDER BY name ASC";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Departments.class);
        }
    }

    @Override
    public List<DepNews> getAllDepartmentNews(int departmentId){
        String sql = "SELECT * FROM news WHERE departmentId =:departmentId";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("departmentId", departmentId).throwOnMappingFailure(false).executeAndFetch(DepNews.class);
        }
    }

    @Override
    public void addDepartmentToUser(Departments department, Users user){
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
    public List<Users> getAllUsersForADepartment(int department_id){
        ArrayList<Users> allUsers = new ArrayList<>();
        String matchToGetTheUserIds = "SELECT user_id FROM departments_users WHERE department_id =:department_id";
        try(Connection conn = sql2o.open()){
            List<Integer> allUserIds = conn.createQuery(matchToGetTheUserIds)
                    .addParameter("department_id", department_id)
                    .executeAndFetch(Integer.class);
            for(Integer user_id : allUserIds){
                String getFromUsers = "SELECT * FROM users WHERE id =:user_id";
                allUsers.add(conn.createQuery(getFromUsers)
                .addParameter("user_id", user_id)
                .executeAndFetchFirst(Users.class));
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return allUsers;
    }

    public void clearAll(){
        String sql = "DELETE from departments";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

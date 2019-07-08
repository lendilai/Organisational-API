package dao;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import models.Departments;

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
        String sql = "SELECT * FROM departments";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).executeAndFetch(Departments.class);
        }
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

package dao;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import models.Departments;

public class SqlDepartmentsDao implements DepartmentsDao {
    private final Sql2o sql2o;

    public SqlDepartmentsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Departments departments){
        String sql = "INSERT INTO departments(name, description, user_no) VALUES (:name, :description, :userNo)";
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
}

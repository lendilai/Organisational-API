package dao;

import models.Users;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class SqlUserDao implements UserDao {

    private final Sql2o sql2o;

    public SqlUserDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users user){
        String sql = "INSERT INTO users(name, position, departmentId) VALUES (:name, :position, :departmentId)";
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
}

package dao;

import models.Users;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlUserDao implements UserDao {

    private final Sql2o sql2o;

    public SqlUserDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users user){
        String sql = "INSERT INTO users(name, position) VALUES (:name, :position)";
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

}

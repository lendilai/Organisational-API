package dao;

import models.DepNews;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class SqlDepNewsDao implements DepNewsDao {

    private final Sql2o sql2o;

    public SqlDepNewsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(DepNews depNews){
        String sql ="INSERT INTO news(title, content, importance, type, departmentId) VALUES (:title, :content, :importance, 'Department news', :departmentId)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true).bind(depNews).executeUpdate().getKey();
            depNews.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}

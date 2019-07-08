package dao;

import models.GenNews;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

public class SqlGenNewsDao implements GenNewsDao{
    private final Sql2o sql2o;

    public SqlGenNewsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(GenNews genNews){
        String sql = "INSERT INTO news(title, content, importance, type) VALUES (:title, :content, :importance, 'General news')";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true).bind(genNews).throwOnMappingFailure(false).executeUpdate().getKey();
            genNews.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    public void clearAll(){
        String sql = "DELETE from news WHERE type = 'General news'";
        try(Connection conn = sql2o.open()){
            conn .createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}

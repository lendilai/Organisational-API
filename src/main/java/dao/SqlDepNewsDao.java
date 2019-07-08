package dao;

import models.DepNews;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlDepNewsDao implements DepNewsDao {

    private final Sql2o sql2o;

    public SqlDepNewsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(DepNews depNews){
        String sql ="INSERT INTO news(title, content, importance, type, departmentId) VALUES (:title, :content, :importance, 'Department news', :departmentId)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true).bind(depNews).throwOnMappingFailure(false).executeUpdate().getKey();
            depNews.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public DepNews findById(int id){
        String sql = "SELECT * FROM news WHERE id =:id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(DepNews.class);
        }
    }

    @Override
    public void updateDepNews(int id, String newTitle, String newContent, String newImportance){
        String sql = "UPDATE news SET (title, content, importance) = (:title, :content, :importance) WHERE id =:id";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("title", newTitle)
                    .addParameter("content", newContent)
                    .addParameter("importance", newImportance)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<DepNews> getAllDepNews(int departmentId){
        String sql = "SELECT * FROM news WHERE departmentId =:departmentId";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql).addParameter("departmentId", departmentId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(DepNews.class);
        }
    }

    public void clearAll(){
        String sql = "DELETE from news WHERE type = 'Department news'";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

}

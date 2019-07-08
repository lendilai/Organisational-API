package models;

import java.util.Objects;

public class DepNews extends News{
    private int departmentId;
    private int id;

    public DepNews(String title, String content, String importance, int departmentId){
        super(title, content, importance);
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepNews)) return false;
        if (!super.equals(o)) return false;
        DepNews depNews = (DepNews) o;
        return getDepartmentId() == depNews.getDepartmentId() &&
                getId() == depNews.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDepartmentId(), getId());
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

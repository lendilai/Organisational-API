package models;

import java.util.Objects;

public class DepNews extends News{
    private int departmentId;

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
        return departmentId == depNews.departmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departmentId);
    }

    public int getDepartmentId() {
        return departmentId;
    }
}

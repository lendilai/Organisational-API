package models;

import java.util.Objects;

public abstract class News {
    public String title;
    public String content;
    public String importance;

    public News(String title, String content, String importance){
        this.title = title;
        this.importance = importance;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return title.equals(news.title) &&
                content.equals(news.content) &&
                importance.equals(news.importance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, importance);
    }

    public String getContent() {
        return content;
    }

    public String getImportance() {
        return importance;
    }

    public String getTitle() {
        return title;
    }
}

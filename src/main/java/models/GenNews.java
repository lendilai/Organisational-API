package models;

public class GenNews extends News{

    private int id;

    public GenNews(String title, String content, String importance){
        super(title, content, importance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

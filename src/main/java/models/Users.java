package models;

import java.util.Objects;

public class Users {
    private String name;
    private String position;
    private String url;
    private int id;

    public Users(String name, String position, String url){
        this.name = name;
        this.position = position;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return getId() == users.getId() &&
                getName().equals(users.getName()) &&
                getPosition().equals(users.getPosition()) &&
                getUrl().equals(users.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition(), getUrl(), getId());
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package models;

import java.util.Objects;

public class Users {
    private String name;
    private String position;
    private int id;

    public Users(String name, String position){
        this.name = name;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return getId() == users.getId() &&
                getName().equals(users.getName()) &&
                getPosition().equals(users.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition(), getId());
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

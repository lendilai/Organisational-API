package models;

import java.util.Objects;

public class Users {
    private String name;
    private String position;
    private int departmentId;
    private int id;

    public Users(String name, String position, int departmentId){
        this.name = name;
        this.position = position;
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return getDepartmentId() == users.getDepartmentId() &&
                getId() == users.getId() &&
                getName().equals(users.getName()) &&
                getPosition().equals(users.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition(), getDepartmentId(), getId());
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
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

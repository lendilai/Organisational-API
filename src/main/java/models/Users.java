package models;

import java.util.Objects;

public class Users {
    private String name;
    private String position;
    private int departmentId;

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
        return departmentId == users.departmentId &&
                name.equals(users.name) &&
                position.equals(users.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, departmentId);
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
}

package models;

import java.util.Objects;

public class Departments {
    private String name;
    private String description;
    private int user_no;
    private int id;
    private final int MAX_USER_NO_PER_DEP = 100;

    public Departments(String name, String description, int user_no){
        this.name = name;
        this.description = description;
        this.user_no = user_no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departments)) return false;
        Departments that = (Departments) o;
        return getUser_no() == that.getUser_no() &&
                getId() == that.getId() &&
                getMAX_USER_NO_PER_DEP() == that.getMAX_USER_NO_PER_DEP() &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getUser_no(), getId(), getMAX_USER_NO_PER_DEP());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getUser_no() {
        return user_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMAX_USER_NO_PER_DEP() {
        return MAX_USER_NO_PER_DEP;
    }
}

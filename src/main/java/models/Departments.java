package models;

import java.util.Objects;

public class Departments {
    private String name;
    private String description;
    private int userNo;
    private int id;
    private final int MAX_USER_NO_PER_DEP = 100;

    public Departments(String name, String description, int userNo){
        this.name = name;
        this.description = description;
        this.userNo = userNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departments)) return false;
        Departments that = (Departments) o;
        return getUserNo() == that.getUserNo() &&
                getMAX_USER_NO_PER_DEP() == that.getMAX_USER_NO_PER_DEP() &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getUserNo(), getMAX_USER_NO_PER_DEP());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getUserNo() {
        return userNo;
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

package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    @Test
    public void User_instantiatesCorrectly() {
        Users newUser = new Users("John Doe", "CTO", 3);
        assertTrue(newUser instanceof Users);
    }

    @Test
    public void User_instantiatesWithCorrectValues() {
        Users newUser = new Users("John Doe", "CTO", 3);
        assertEquals("John Doe", newUser.getName());
        assertEquals("CTO", newUser.getPosition());
        assertEquals(3, newUser.getDepartmentId());
    }
}
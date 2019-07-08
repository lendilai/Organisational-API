package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    @Test
    public void User_instantiatesCorrectly() {
        Users newUser = new Users("John Doe", "CTO", "http://duxhg.com");
        assertTrue(newUser instanceof Users);
    }

    @Test
    public void User_instantiatesWithCorrectValues() {
        Users newUser = new Users("John Doe", "CTO", "http://duxhg.com");
        assertEquals("John Doe", newUser.getName());
        assertEquals("CTO", newUser.getPosition());
    }
}
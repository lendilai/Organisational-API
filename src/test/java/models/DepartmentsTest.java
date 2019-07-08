package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentsTest {

    @Test
    public void Departments_instantiatesCorrectly() {
        Departments newDep = new Departments("Marketing", "Gain investors", 16);
        assertTrue(newDep instanceof Departments);
    }

    @Test
    public void Departments_instantiatesWithCorrectValues() {
        Departments newDep = new Departments("Marketing", "Gain investors", 16);
        assertEquals("Marketing", newDep.getName());
        assertEquals("Gain investors", newDep.getDescription());
        assertEquals(16, newDep.getUserNo());
        assertEquals(100, newDep.getMAX_USER_NO_PER_DEP());
    }
}
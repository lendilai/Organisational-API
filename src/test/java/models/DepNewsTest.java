package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepNewsTest {

    @Test
    public void DepNews_instantiatesCorrectly() {
        DepNews news = new DepNews("Admin feature", "The deadline for completion of the feature has been changed to this Thursday", "Very important", 3);
        assertTrue(news instanceof DepNews);
    }

    @Test
    public void DepNews_instantiatesWothCorrectValues() {
        DepNews news = new DepNews("Admin feature", "The deadline for completion of the feature has been changed to this Thursday", "Very important", 3);
        assertEquals("Admin feature", news.getTitle());
        assertEquals("The deadline for completion of the feature has been changed to this Thursday", news.getContent());
        assertEquals("Very important", news.getImportance());
        assertEquals(3, news.getDepartmentId());
    }
}
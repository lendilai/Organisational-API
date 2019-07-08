package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenNewsTest {

    @Test
    public void GenNews_instantiatesCorrectly() {
        GenNews news = new GenNews("Elections", "Elections will be held next week on Monday", "Very important");
        assertTrue(news instanceof GenNews);
    }

    @Test
    public void GenNews_instantiatesWithCorrectValues() {
        GenNews news = new GenNews("Elections", "Elections will be held next week on Monday", "Very important");
        assertEquals("Elections", news.getTitle());
        assertEquals("Elections will be held next week on Monday", news.getContent());
        assertEquals("Very important", news.getImportance());
    }
}
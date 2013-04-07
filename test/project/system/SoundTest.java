package project.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class Sound.
 */
public class SoundTest {
    private User user1, user2;
    private Sound sound1, sound2;
    
    @Before
    public void setUp() {
        user1 = new User("a", "a", "a", "a");
        sound1 = new Sound("http://google.com", "1/1/2012", user1);
        
        user2 = new User("b", "b", "b", "b");
        sound2 = new Sound(new Link("http://facebook.com"), new SimpleDate("3/2/2013"), user2);
    }
    
    /**
     * Test of getLink method, of class Sound.
     */
    @Test
    public void testGetLink() {
        assertEquals(new Link("http://google.com"), sound1.getLink());
        assertEquals(new Link("http://facebook.com"), sound2.getLink());
    }

    /**
     * Test of getCreationDate method, of class Sound.
     */
    @Test
    public void testGetCreationDate() {
        assertEquals(new SimpleDate("1/1/2012"), sound1.getCreationDate());
    }

    /**
     * Test of getAuthor method, of class Sound.
     */
    @Test
    public void testGetAuthor() {
        assertEquals(user1, sound1.getAuthor());
        assertEquals(user2, sound2.getAuthor());
    }

    /**
     * Test of setLink method, of class Sound.
     */
    @Test
    public void testSetLink() {
        sound1.setLink(new Link("http://bla.com"));
        assertEquals(new Link("http://bla.com"), sound1.getLink());
    }

    /**
     * Test of setCreationDate method, of class Sound.
     */
    @Test
    public void testSetCreationDate() {
        sound1.setCreationDate(new SimpleDate("5/5/2005"));
        assertEquals(new SimpleDate("5/5/2005"), sound1.getCreationDate());
    }

    /**
     * Test of setAuthor method, of class Sound.
     */
    @Test
    public void testSetAuthor() {
        sound1.setAuthor(user2);
        assertEquals(user2, sound1.getAuthor());
    }

    /**
     * Test of equals method, of class Sound.
     */
    @Test
    public void testEquals() {
        assertFalse(sound1.equals(sound2));
        assertEquals(new Sound("http://google.com", "1/1/2012", user1), sound1);
    }

    /**
     * Test of compareTo method, of class Sound.
     */
    @Test
    public void testCompareTo() {
        assertTrue(sound1.compareTo(sound2) > 0);
        assertTrue(sound2.compareTo(sound1) < 0);
        assertTrue(sound1.compareTo(sound1) == 0);
        assertTrue(sound2.compareTo(sound2) == 0);
    }
}

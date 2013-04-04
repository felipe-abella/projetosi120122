package project.system;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import project.exceptions.InvalidLinkException;

/**
 * Tests the Link class.
 */
public class LinkTest {
    private Link link1, link2, link3;
    
    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        link1 = new Link("http://google.com");
        link2 = new Link("http://facebook.com");
        link3 = new Link("http://gmail.com");
    }
    
    /**
     * Tests for InvalidLinkException from constructor.
     */
    @Test(expected = InvalidLinkException.class)
    public void testInvalidLink1() {
        new Link("");
    }
    
    /**
     * Tests for InvalidLinkException from constructor.
     */
    @Test(expected = InvalidLinkException.class)
    public void testInvalidLink2() {
        new Link(null);
    }
    
    /**
     * Test getters and setters.
     */
    @Test
    public void testGettersAndSetters() {
        assertEquals("http://google.com", link1.getPath());
        link1.setPath("http://google2.com");
        assertEquals("http://google2.com", link1.getPath());
    }

    /**
     * Test of equals method.
     */
    @Test
    public void testEquals() {
        assertFalse(link1.equals(link2));
        assertFalse(link1.equals(new Link("http://Google.com")));
        assertEquals(link1, link1);
        assertEquals(link1, new Link("http://google.com"));
    }

    /**
     * Test of compareTo method.
     */
    @Test
    public void testCompareTo() {
        Link link3 = new Link("http://a.com");
        Link link4 = new Link("http://b.com");
        Link link5 = new Link("http://c.com");
        Link link6 = new Link("http://c.com");
        assertTrue(link6.compareTo(link6) == 0);
        assertTrue(link3.compareTo(link4) < 0);
        assertTrue(link4.compareTo(link3) > 0);
        assertTrue(link4.compareTo(link5) < 0);
    }
}

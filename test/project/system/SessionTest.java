package project.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Session class.
 */
public class SessionTest {
    private User user, user2;
    private Session session, session2;
    
    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        user = new User("a", "a", "a", "a");
        user2 = new User("b", "b", "b", "b");
        session = new Session(user);
        session2 = new Session(user2);
    }

    /**
     * Test of getUser method.
     */
    @Test
    public void testGetUser() {
        assertEquals(user, session.getUser());
    }

    /**
     * Test of equals method.
     */
    @Test
    public void testEquals() {
        assertEquals(session, session);
        assertEquals(session, new Session(user));
        assertFalse(session.equals(new Session(user2)));
    }

    /**
     * Test of compareTo method.
     */
    @Test
    public void testCompareTo() {
        assertEquals(session.compareTo(session2) < 0,
                session.getUser().compareTo(session2.getUser()) < 0);
    }
}

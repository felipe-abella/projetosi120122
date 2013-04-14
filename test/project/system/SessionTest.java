package project.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import project.system.authentication.AuthChannel;
import project.system.authentication.NotLoggedInException;
import project.system.authentication.password.PasswordAuth;
import project.system.authentication.password.PasswordAuthChannel;
import project.system.authentication.password.PasswordLoginFailedException;

/**
 * Tests the Session class.
 */
public class SessionTest {

    private User user, user2;
    private Session session, session2;
    private PasswordAuthChannel channel, channel2;

    /**
     * Set up the tests.
     * @throws NotLoggedInException if an error occurs
     * @throws PasswordLoginFailedException if an error occurs
     */
    @Before
    public void setUp() throws NotLoggedInException, PasswordLoginFailedException {
        user = new User("a", "a", "a");
        channel = PasswordAuth.getInstance().getChannel(user);
        PasswordAuth.getInstance().registerUser(user, "a");
        channel.enterPassword("a");
        channel.login();

        user2 = new User("b", "b", "b");
        PasswordAuth.getInstance().registerUser(user2, "b");
        channel2 = PasswordAuth.getInstance().getChannel(user2);
        channel2.enterPassword("b");
        channel2.login();

        session = new Session(user, channel);
        session2 = new Session(user2, channel2);
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
     * @throws NotLoggedInException if an error occurs
     * @throws PasswordLoginFailedException if an error occurs
     */
    @Test
    public void testEquals() throws NotLoggedInException, PasswordLoginFailedException {
        assertEquals(session, session);
        assertEquals(session, new Session(user, channel));

        PasswordAuthChannel channel3 = PasswordAuth.getInstance().getChannel(user2);
        channel3.enterPassword("b");
        channel3.login();

        assertFalse(session.equals(new Session(user2, channel3)));
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

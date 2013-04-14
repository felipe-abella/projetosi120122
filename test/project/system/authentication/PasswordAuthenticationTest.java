package project.system.authentication;

import org.junit.Assert;
import project.system.authentication.password.PasswordAuthChannel;
import org.junit.Test;
import project.system.User;
import static org.junit.Assert.*;
import project.system.authentication.password.PasswordAuth;
import project.system.authentication.password.PasswordLoginFailedException;

/**
 * Test classes related to password authentication.
 */
public class PasswordAuthenticationTest {

    /**
     * Test classes related to password authentication.
     */
    @Test
    public void testPasswordAuthentication() throws PasswordLoginFailedException, LogoutFailedException {
        PasswordAuth auth = PasswordAuth.getInstance();
        User user = new User("a", "A", "a@a");
        auth.registerUser(user, "myPassword");

        PasswordAuthChannel authChannel = auth.getChannel(user);

        authChannel.enterPassword("myPassword");
        authChannel.login();

        authChannel.enterPassword("blabla");
        authChannel.login(); /* Already logged-in, shouldn't fail */

        authChannel.logout();
        try {
            authChannel.login();
            Assert.fail("Login should have thrown an exception due to wrong password!");
        } catch (PasswordLoginFailedException ex) {
        }

        authChannel.enterPassword("myPassword");
        authChannel.login();
    }
}

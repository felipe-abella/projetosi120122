package project.system.authentication.password;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.system.Project;
import project.system.Session;
import project.system.User;
import project.system.authentication.AuthenticationException;
import project.system.authentication.Authenticator;

/**
 * Implements a simple password authenticator.
 */
public class PasswordAuth implements Authenticator {

    private static PasswordAuth instance = null;
    private Map<User, String> passwords;

    private PasswordAuth() {
        passwords = new HashMap<User, String>();
    }

    /**
     * Returns the singleton instance of a password authenticator.
     *
     * If there isn't any, a new instance will be created.
     *
     * @return the instance
     */
    public static PasswordAuth getInstance() {
        if (instance == null) {
            Project project = Project.getInstance();
            instance = new PasswordAuth();
            project.registerAuthenticator(instance);
        }
        return instance;
    }

    /**
     * Returns if a user has a registered password.
     *
     * @param user the user
     * @return if his registered
     */
    public boolean isRegistered(User user) {
        return passwords.containsKey(user);
    }

    /**
     * Register a user with a password.
     * 
     * If the user is already registered, it's password will be changed.
     * 
     * @param user the user
     * @param password the password
     */
    public void registerUser(User user, String password) {
        passwords.put(user, password);
    }

    /**
     * Changes a user password.
     * 
     * If the user is not registered, it will be now.
     * 
     * @param user the user
     * @param password his password
     */
    public void setPassword(User user, String password) {
        if (!isRegistered(user)) {
            registerUser(user, password);
        } else {
            passwords.put(user, password);
        }
    }

    /**
     * Creates an authentication channel for a user.
     * 
     * @param user the user
     * @return the channel
     */
    @Override
    public PasswordAuthChannel getChannel(User user) {
        return new PasswordAuthChannel(user);
    }

    /**
     * Returns if the user have a registered password.
     * @param user the user
     * @return if he's registered
     */
    public boolean havePassword(User user) {
        return passwords.containsKey(user);
    }

    /**
     * Check if a password matches a user's password.
     * @param user the user
     * @param password the password attempt
     * @return if they match
     */
    public boolean checkPassword(User user, String password) {
        if (!passwords.containsKey(user)) {
            return false;
        }
        return passwords.get(user).equals(password);
    }

    /**
     * Convenience method for opening a new password-authenticated session.
     * 
     * @param user the user
     * @param password the password
     * @return the opened session (or null if something wrong happens)
     */
    public static Session openNewSession(User user, String password) {
        try {
            PasswordAuthChannel channel = PasswordAuth.getInstance().getChannel(user);
            channel.enterPassword(password);
            channel.login();
            return Project.getInstance().openNewSession(user, channel);
        } catch (AuthenticationException ex) {
            return null;
        }
    }
}

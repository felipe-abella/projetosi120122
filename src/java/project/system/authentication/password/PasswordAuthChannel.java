package project.system.authentication.password;

import java.util.LinkedList;
import java.util.List;
import project.system.User;
import project.system.authentication.AuthChannelListener;
import project.system.authentication.AuthChannel;
import project.system.authentication.AuthChannelState;
import project.system.authentication.LogoutFailedException;

/**
 * Implements a password authentication channel for a given user.
 */
public class PasswordAuthChannel implements AuthChannel {

    private User user;
    private String password;
    private AuthChannelState state;
    private List<AuthChannelListener> listeners;

    /**
     * Creates the channel for a given user.
     *
     * @param user the user
     */
    PasswordAuthChannel(User user) {
        this.user = user;
        password = null;
        state = AuthChannelState.CLOSED;
        listeners = new LinkedList<AuthChannelListener>();
    }

    /**
     * Returns the user
     *
     * @return the user
     */
    @Override
    public User getUser() {
        return user;
    }

    private void open() {
        state = AuthChannelState.OPEN;
        for (AuthChannelListener listener : listeners) {
            listener.opened(this);
        }
    }

    private void close() {
        state = AuthChannelState.CLOSED;
        for (AuthChannelListener listener : listeners) {
            listener.closed(this);
        }
    }

    /**
     * Attempts to login using the entered password.
     *
     * @throws PasswordLoginFailedException if the login fails
     */
    @Override
    public void login() throws PasswordLoginFailedException {
        if (state == AuthChannelState.CLOSED) {
            if (!getAuthenticator().havePassword(user)) {
                throw new NoPasswordException();
            }
            if (!getAuthenticator().checkPassword(user, password)) {
                throw new WrongPasswordException();
            }
            open();
        }
    }

    /**
     * Logout the user.
     */
    @Override
    public void logout() {
        if (state == AuthChannelState.OPEN) {
            close();
        }
    }

    /**
     * Enter the password to be used on the login attempt.
     *
     * @param password the password
     */
    public void enterPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the authentication state.
     *
     * @return the state
     */
    @Override
    public AuthChannelState getState() {
        return state;
    }

    /**
     * Returns the authenticator for this channel.
     *
     * @return the password authenticator
     */
    @Override
    public PasswordAuth getAuthenticator() {
        return PasswordAuth.getInstance();
    }

    /**
     * Adds an auth channel listener for this channel.
     *
     * @param listener the listener
     */
    @Override
    public void addListener(AuthChannelListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an auth channel listener.
     *
     * @param listener the listener
     */
    @Override
    public void removeListener(AuthChannelListener listener) {
        listeners.remove(listener);
    }
}

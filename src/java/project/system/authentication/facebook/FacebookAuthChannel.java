package project.system.authentication.facebook;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import project.system.User;
import project.system.authentication.AuthChannel;
import project.system.authentication.AuthChannelListener;
import project.system.authentication.AuthChannelState;
import project.system.authentication.LoginFailedException;
import project.system.authentication.LogoutFailedException;

/**
 * Implements a facebook authentication channel for a given user.
 */
public class FacebookAuthChannel implements AuthChannel, Serializable {

    private User user;
    private AuthChannelState state;
    private List<AuthChannelListener> listeners;
    private String inputFaceid, inputAuthToken;

    /**
     * Creates a facebook authentication channel for the user.
     *
     * @param user the user
     */
    FacebookAuthChannel(User user) {
        this.user = user;
        state = AuthChannelState.CLOSED;
        listeners = new LinkedList<AuthChannelListener>();
        inputFaceid = inputAuthToken = null;
    }

    /**
     * Returns the user.
     *
     * @return the user
     */
    @Override
    public User getUser() {
        return user;
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
     * Returns this channel's authenticator.
     *
     * @return the authenticator
     */
    @Override
    public FacebookAuth getAuthenticator() {
        return FacebookAuth.getInstance();
    }

    /**
     * Enters the facebook account id.
     *
     * This id will be used in the next login attempt.
     *
     * @param inputFaceid the facebook id
     */
    public void enterFaceid(String inputFaceid) {
        this.inputFaceid = inputFaceid;
    }

    /**
     * Enters the facebook authentication token.
     *
     * This token will be used to confirm user veracity on the server-side and
     * enforce stronger security.
     *
     * @param inputAuthToken the facebook auth token
     */
    public void enterInputAuthToken(String inputAuthToken) {
        this.inputAuthToken = inputAuthToken;
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
     * Attempts to login using given facebook id and token.
     *
     * @throws LoginFailedException if the login fails for some reason
     */
    @Override
    public void login() throws LoginFailedException {
        if (state == AuthChannelState.CLOSED) {
            if (!FacebookAuth.getInstance().isRegistered(user)
                    || !FacebookAuth.getInstance().getFaceid(user).equals(inputFaceid)) {
                throw new FacebookLoginFailedException();
            }
            open();
        }
    }

    /**
     * Attempts to logout using given facebook id and token.
     *
     * @throws LogoutFailedException if the logout fails for some reason
     */
    @Override
    public void logout() throws LogoutFailedException {
        if (state == AuthChannelState.OPEN) {
            close();
        }
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

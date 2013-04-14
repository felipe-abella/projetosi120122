package project.system.authentication;

import project.system.User;

/**
 * Represents a channel to authenticate a user in some authentication mechanism.
 */
public interface AuthChannel {

    /**
     * Returns the user to authenticate.
     *
     * @return the user
     */
    public User getUser();

    /**
     * Returns the authentication state.
     *
     * @return the state (open or closed)
     */
    public AuthChannelState getState();

    /**
     * Returns the authenticator that created this channel.
     *
     * @return the authenticator
     */
    public Authenticator getAuthenticator();

    /**
     * Attempts to authenticate using this channel's data.
     *
     * @throws LoginFailedException if the login failed
     */
    public void login() throws LoginFailedException;

    /**
     * Attempts to de-authenticate (i.e., logout) the user.
     *
     * @throws LogoutFailedException if the logout failed
     */
    public void logout() throws LogoutFailedException;

    /**
     * Adds a listener to this channel's events.
     *
     * @param listener the listener
     */
    public void addListener(AuthChannelListener listener);

    /**
     * Remove a listener of this channel.
     *
     * @param listener the listener
     */
    public void removeListener(AuthChannelListener listener);
}

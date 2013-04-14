package project.system.authentication;

import project.system.User;

public interface AuthChannel {

    public User getUser();
    
    public AuthChannelState getState();

    public Authenticator getAuthenticator();

    public void login() throws LoginFailedException;

    public void logout() throws LogoutFailedException;

    public void addListener(AuthChannelListener listener);

    public void removeListener(AuthChannelListener listener);
}

package project.system.authentication;

import project.system.User;

/**
 * Represents an authenticator for an authentication method.
 */
public interface Authenticator {
    
    /**
     * Creates an authentication channel for an user.
     * 
     * @param user the user
     * @return the auth channel
     */
    public AuthChannel getChannel(User user);
}

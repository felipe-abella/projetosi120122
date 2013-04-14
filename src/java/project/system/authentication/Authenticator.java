package project.system.authentication;

import project.system.User;

public interface Authenticator {
    
    public AuthChannel getChannel(User user);
}

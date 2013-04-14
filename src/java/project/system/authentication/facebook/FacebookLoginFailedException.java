package project.system.authentication.facebook;

import project.system.authentication.LoginFailedException;

public class FacebookLoginFailedException extends LoginFailedException {
    public FacebookLoginFailedException() {
        super("Failed to login using facebook!");
    }
    
    public FacebookLoginFailedException(String msg) {
        super(msg);
    }
}

package project.system.authentication.password;

import project.system.authentication.LoginFailedException;

public class PasswordLoginFailedException extends LoginFailedException {
    public PasswordLoginFailedException() {
        super("Failed to login using password!");
    }
    
    public PasswordLoginFailedException(String msg) {
        super(msg);
    }
}

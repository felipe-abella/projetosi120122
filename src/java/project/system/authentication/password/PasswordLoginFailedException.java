package project.system.authentication.password;

import project.system.authentication.LoginFailedException;

/**
 * Exception raised when a password-authenticated login fails.
 */
public class PasswordLoginFailedException extends LoginFailedException {

    /**
     * Creates the exception.
     */
    public PasswordLoginFailedException() {
        super("Failed to login using password!");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public PasswordLoginFailedException(String msg) {
        super(msg);
    }
}

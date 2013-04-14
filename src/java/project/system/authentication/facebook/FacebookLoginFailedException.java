package project.system.authentication.facebook;

import project.system.authentication.LoginFailedException;

/**
 * Exception raised when a facebook logins fails.
 */
public class FacebookLoginFailedException extends LoginFailedException {

    /**
     * Creates the exception.
     */
    public FacebookLoginFailedException() {
        super("Failed to login using facebook!");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public FacebookLoginFailedException(String msg) {
        super(msg);
    }
}

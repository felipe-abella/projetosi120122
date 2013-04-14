package project.system.authentication;

/**
 * Exception raised when a login attempt fails.
 */
public class LoginFailedException extends AuthenticationException {

    /**
     * Creates the exception.
     */
    public LoginFailedException() {
        super("Login failed");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public LoginFailedException(String msg) {
        super(msg);
    }
}

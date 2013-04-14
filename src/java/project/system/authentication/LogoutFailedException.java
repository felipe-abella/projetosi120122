package project.system.authentication;

/**
 * Exception raised when a logout attempt fails.
 */
public class LogoutFailedException extends AuthenticationException {

    /**
     * Creates the exception.
     */
    public LogoutFailedException() {
        super("Logout failed");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public LogoutFailedException(String msg) {
        super(msg);
    }
}

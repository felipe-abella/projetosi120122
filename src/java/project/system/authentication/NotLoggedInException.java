package project.system.authentication;

/**
 * Exception raised when we expect a channel to be open, but it's not.
 */
public class NotLoggedInException extends AuthenticationException {

    /**
     * Creates the exception.
     */
    public NotLoggedInException() {
        super("The authentication channel is closed!");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public NotLoggedInException(String msg) {
        super(msg);
    }
}

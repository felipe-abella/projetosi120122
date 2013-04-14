package project.system.authentication;

/**
 * Exception raised when an authentication error happens.
 * 
 * The common authentication exceptions are login and logout fail.
 */
public class AuthenticationException extends Exception {

    /**
     * Creates the exception.
     */
    public AuthenticationException() {
        super("An authentication problem happened!");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public AuthenticationException(String msg) {
        super(msg);
    }
}

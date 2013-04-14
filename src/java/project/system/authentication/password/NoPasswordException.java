package project.system.authentication.password;

/**
 * Exception raised when we except an registered password, but there isn't any.
 */
public class NoPasswordException extends PasswordLoginFailedException {

    /**
     * Creates the exception.
     */
    public NoPasswordException() {
        super("The user have no registered password!");
    }

    /**
     * Creates the exception with a personalized message.
     *
     * @param msg the message
     */
    public NoPasswordException(String msg) {
        super(msg);
    }
}

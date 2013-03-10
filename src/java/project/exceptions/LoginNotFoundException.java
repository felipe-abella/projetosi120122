package project.exceptions;

/**
 * Exception raised when a requested login is not found.
 */
public class LoginNotFoundException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public LoginNotFoundException() {
        super("Login inexistente");
    }
}

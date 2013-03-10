package project.exceptions;

/**
 * Exception raised when a password is invalid.
 */
public class InvalidPasswordException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidPasswordException() {
        super("Login inválido");
    }
}

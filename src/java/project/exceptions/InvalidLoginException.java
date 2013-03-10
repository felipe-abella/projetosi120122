package project.exceptions;

/**
 * Exception raised when a login is invalid.
 */
public class InvalidLoginException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidLoginException() {
        super("Login inválido");
    }
}

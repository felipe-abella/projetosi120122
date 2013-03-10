package project.exceptions;

/**
 * Exception raised when a session ID is invalid.
 */
public class InvalidSessionIdException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidSessionIdException() {
        super("Sessão inválida");
    }
}

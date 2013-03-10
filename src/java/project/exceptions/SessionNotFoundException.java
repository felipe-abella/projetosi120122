package project.exceptions;

/**
 * Exception raised when a search for a session fails.
 */
public class SessionNotFoundException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public SessionNotFoundException() {
        super("Sessão inexistente");
    }
}

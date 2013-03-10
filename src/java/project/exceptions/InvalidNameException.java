package project.exceptions;

/**
 * Exception raised when a name invalid.
 */
public class InvalidNameException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidNameException() {
        super("Nome inválido");
    }
}

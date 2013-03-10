package project.exceptions;

/**
 * Exception raised when an email is invalid.
 */
public class InvalidEmailException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidEmailException() {
        super("Email inválido");
    }
}

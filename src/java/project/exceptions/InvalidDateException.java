package project.exceptions;

/**
 * Exception raised when a date is invalid.
 */
public class InvalidDateException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidDateException() {
        super("Data inválida");
    }
}

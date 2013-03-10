package project.exceptions;

/**
 * Exception raised when a creation date is invalid.
 */
public class InvalidCreationDateException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidCreationDateException() {
        super("Data de Criação inválida");
    }
}

package project.exceptions;

/**
 * Exception raised when we try to search for an invalid circle id.
 */
public class InvalidCircleIdException extends IllegalArgumentException {
    
    /**
     * Constructs the exception.
     */
    public InvalidCircleIdException() {
        super("Lista inválida");
    }
}

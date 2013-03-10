package project.exceptions;

/**
 * Exception raised when an attribute name is invalid.
 */
public class InvalidAttributeException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidAttributeException() {
        super("Atributo inválido");
    }
}

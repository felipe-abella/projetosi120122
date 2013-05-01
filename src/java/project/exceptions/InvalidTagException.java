package project.exceptions;

/**
 * Exception raised when we search for an invalid tag.
 */
public class InvalidTagException extends IllegalArgumentException {

    /**
     * Constructs the exception.
     */
    public InvalidTagException() {
        super("Tag inválida");
    }
}

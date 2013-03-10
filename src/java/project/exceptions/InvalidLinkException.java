package project.exceptions;

/**
 * Exception raised when a link is invalid.
 */
public class InvalidLinkException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidLinkException() {
        super("Som inválido");
    }
}

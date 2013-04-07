package project.exceptions;

/**
 * Exception raised when a search for a circle fails.
 */
public class CircleNotFoundException extends IllegalArgumentException {

    /**
     * Constructs the exception.
     */
    public CircleNotFoundException() {
        super("Lista inexistente");
    }
}

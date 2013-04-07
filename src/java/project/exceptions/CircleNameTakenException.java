package project.exceptions;

/**
 * Exception raised when we try to add a circle with an already existing name.
 */
public class CircleNameTakenException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public CircleNameTakenException() {
        super("Nome já escolhido");
    }
}

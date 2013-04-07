package project.exceptions;

/**
 * Exception raised when an invalid circle name is looked for.
 */
public class InvalidCircleNameException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidCircleNameException() {
        super("Nome inválido");
    }
}

package project.exceptions;

/**
 * Exception raised when a following-relation is invalid.
 */
public class InvalidFollowingException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidFollowingException() {
        super("Relação seguidor/fonte inválida!");
    }
}

package project.exceptions;

/**
 * Exception raised when we try to search for an invalid user id.
 */
public class InvalidUserIdException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidUserIdException() {
        super("Usuário inválido");
    }
}

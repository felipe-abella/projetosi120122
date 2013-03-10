package project.exceptions;

/**
 * Exception raised when a user is not found.
 */
public class UserNotFoundException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public UserNotFoundException() {
        super("Usuário inexistente");
    }
}

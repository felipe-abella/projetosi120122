package project.exceptions;

/**
 * Exception raised when we try to add a user to a circle it already belongs to.
 */
public class UserAlreadyInCircleException extends IllegalArgumentException {

    /**
     * Creates the exception.
     */
    public UserAlreadyInCircleException() {
        super("Usuário já existe nesta lista");
    }
}

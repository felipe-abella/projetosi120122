package project.exceptions;

/**
 * Exception raised when we try to add a user to one of his own circles.
 */
public class CircleCantAddOwnerException extends IllegalArgumentException {

    /**
     * Creates the exception.
     */
    public CircleCantAddOwnerException() {
        super("Usuário não pode adicionar-se a própria lista");
    }
}

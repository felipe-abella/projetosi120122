package project.exceptions;

/**
 * Exception raised when a login is already in use.
 */
public class LoginTakenException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public LoginTakenException() {
        super("Já existe um usuário com este login");
    }
}

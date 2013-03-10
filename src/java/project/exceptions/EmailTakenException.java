package project.exceptions;

/**
 * Exception raised when an email is already in use.
 */
public class EmailTakenException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public EmailTakenException() {
        super("Já existe um usuário com este email");
    }
}

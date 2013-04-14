package project.system.authentication.password;

/**
 * Exception raised when a wrong password is received.
 */
public class WrongPasswordException extends PasswordLoginFailedException {

    /**
     * Creates the exception.
     */
    public WrongPasswordException() {
        super("Login and password doesn't match!");
    }
}

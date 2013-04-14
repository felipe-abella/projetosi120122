package project.system.authentication.password;

public class WrongPasswordException extends PasswordLoginFailedException {

    public WrongPasswordException() {
        super("Login and password doesn't match!");
    }
}

package project.system.authentication.password;

public class NoPasswordException extends PasswordLoginFailedException {

    public NoPasswordException() {
        super("The user have no registered password!");
    }
}

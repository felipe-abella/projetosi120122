package project.exceptions;

public class LoginNotFoundException extends IllegalArgumentException {
    public LoginNotFoundException() {
        super("Login inexistente");
    }
}

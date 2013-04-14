package project.system.authentication;

public class LoginFailedException extends AuthenticationException {
    public LoginFailedException() {
        super("Login failed");
    }
    
    public LoginFailedException(String message) {
        super(message);
    }
}

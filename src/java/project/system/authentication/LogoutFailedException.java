package project.system.authentication;

public class LogoutFailedException extends AuthenticationException {
    public LogoutFailedException() {
        super("Logout failed");
    }
    
    public LogoutFailedException(String message) {
        super(message);
    }
}

package project.system.authentication;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super("An authentication problem happened!");
    }
    
    public AuthenticationException(String msg) {
        super(msg);
    }
}

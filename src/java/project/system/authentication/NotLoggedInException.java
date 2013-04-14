package project.system.authentication;

public class NotLoggedInException extends AuthenticationException {
    public NotLoggedInException() {
        super("The authentication channel is closed!");
    }
}

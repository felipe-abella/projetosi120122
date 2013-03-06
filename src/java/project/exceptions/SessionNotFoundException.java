package project.exceptions;

public class SessionNotFoundException extends IllegalArgumentException {
    public SessionNotFoundException() {
        super("Sessão inexistente");
    }
}

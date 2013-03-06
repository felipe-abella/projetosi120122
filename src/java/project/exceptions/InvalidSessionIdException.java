package project.exceptions;

public class InvalidSessionIdException extends IllegalArgumentException {
    public InvalidSessionIdException() {
        super("Sessão inválida");
    }
}

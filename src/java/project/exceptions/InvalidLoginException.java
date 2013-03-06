package project.exceptions;

public class InvalidLoginException extends IllegalArgumentException {
    public InvalidLoginException() {
        super("Login inválido");
    }
}

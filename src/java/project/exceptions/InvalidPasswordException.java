package project.exceptions;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super("Login inválido");
    }
}

package project.exceptions;

public class InvalidEmailException extends IllegalArgumentException {
    public InvalidEmailException() {
        super("Email inválido");
    }
}

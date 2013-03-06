package project.exceptions;

public class InvalidNameException extends IllegalArgumentException {
    public InvalidNameException() {
        super("Nome inválido");
    }
}

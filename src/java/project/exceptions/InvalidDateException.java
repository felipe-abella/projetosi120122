package project.exceptions;

public class InvalidDateException extends IllegalArgumentException {
    public InvalidDateException() {
        super("Data inválida");
    }
}

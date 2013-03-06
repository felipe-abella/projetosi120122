package project.exceptions;

public class InvalidCreationDateException extends IllegalArgumentException {
    public InvalidCreationDateException() {
        super("Data de Criação inválida");
    }
}

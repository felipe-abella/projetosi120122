package project.exceptions;

public class InvalidAttributeException extends IllegalArgumentException {
    public InvalidAttributeException() {
        super("Atributo inválido");
    }
}

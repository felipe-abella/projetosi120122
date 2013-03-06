package project.exceptions;

public class InvalidLinkException extends IllegalArgumentException {
    public InvalidLinkException() {
        super("Som inválido");
    }
}

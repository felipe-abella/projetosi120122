package project.exceptions;

public class InvalidSoundException extends IllegalArgumentException {
    public InvalidSoundException() {
        super("Som inválido");
    }
}

package project.exceptions;

/**
 * Exception raised when a sound is invalid.
 */
public class InvalidSoundException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public InvalidSoundException() {
        super("Som inválido");
    }
}

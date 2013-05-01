package project.exceptions;

/**
 * Exception raised when we don't find a tag.
 */
public class TagNotFoundException extends IllegalArgumentException {

    /**
     * Constructs the exception.
     */
    public TagNotFoundException() {
        super("Tag inexistente");
    }
}

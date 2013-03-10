package project.exceptions;

/**
 * Exception raised when the search for an attribute failed.
 */
public class AttributeNotFoundException extends IllegalArgumentException {
    /**
     * Constructs the exception.
     */
    public AttributeNotFoundException() {
        super("Atributo inexistente");
    }
}

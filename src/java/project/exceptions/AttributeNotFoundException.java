package project.exceptions;

public class AttributeNotFoundException extends IllegalArgumentException {
    public AttributeNotFoundException() {
        super("Atributo inexistente");
    }
}

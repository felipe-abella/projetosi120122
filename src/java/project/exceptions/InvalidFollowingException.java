package project.exceptions;

public class InvalidFollowingException extends IllegalArgumentException {
    public InvalidFollowingException() {
        super("Relação seguidor/fonte inválida!");
    }
}

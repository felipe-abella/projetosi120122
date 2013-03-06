package project.exceptions;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException() {
        super("Usuário inexistente");
    }
}

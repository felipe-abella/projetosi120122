package project.exceptions;

public class LoginTakenException extends IllegalArgumentException {
    public LoginTakenException() {
        super("Já existe um usuário com este login");
    }
}

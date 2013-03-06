package project.exceptions;

public class EmailTakenException extends IllegalArgumentException {
    public EmailTakenException() {
        super("Já existe um usuário com este email");
    }
}

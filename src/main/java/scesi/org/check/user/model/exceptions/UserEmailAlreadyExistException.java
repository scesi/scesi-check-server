package scesi.org.check.user.model.exceptions;

public class UserEmailAlreadyExistException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Email already exist";

    public UserEmailAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}

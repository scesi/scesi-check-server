package scesi.org.check.user.model.exceptions;

public class RolUserAlreadyExistException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Assignment rol already exist";

    public RolUserAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

}

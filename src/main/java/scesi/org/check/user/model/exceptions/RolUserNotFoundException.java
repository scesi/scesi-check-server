package scesi.org.check.user.model.exceptions;

public class RolUserNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Assignment rol not found";

    public RolUserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

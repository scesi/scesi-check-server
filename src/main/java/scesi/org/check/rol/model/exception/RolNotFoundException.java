package scesi.org.check.rol.model.exception;

public class RolNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Role not found";

    public RolNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

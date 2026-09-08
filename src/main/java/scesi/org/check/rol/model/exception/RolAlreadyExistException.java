package scesi.org.check.rol.model.exception;

public class RolAlreadyExistException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Rol already exist";

    public RolAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}

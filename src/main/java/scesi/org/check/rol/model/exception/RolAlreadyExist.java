package scesi.org.check.rol.model.exception;

public class RolAlreadyExist extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Rol already exist";

    public RolAlreadyExist() {
        super(DEFAULT_MESSAGE);
    }
}

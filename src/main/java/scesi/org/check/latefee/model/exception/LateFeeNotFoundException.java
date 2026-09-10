package scesi.org.check.latefee.model.exception;

public class LateFeeNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Late Fee not Found";

    public LateFeeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

package scesi.org.check.event.model.exception;

public class EventAlreadyExistException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Event title already exist";

    public EventAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}

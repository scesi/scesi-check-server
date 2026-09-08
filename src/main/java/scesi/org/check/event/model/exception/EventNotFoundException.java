package scesi.org.check.event.model.exception;

public class EventNotFoundException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Event Not found";

    public EventNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

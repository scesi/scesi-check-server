package scesi.org.check.notification.model.exception;

import jakarta.mail.MessagingException;

public class NotificationLateFeeListenerException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Email dont sended";

    public NotificationLateFeeListenerException(MessagingException e) {
        super(DEFAULT_MESSAGE, e);
    }
}

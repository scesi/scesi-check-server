package scesi.org.check.notification.listener;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import scesi.org.check.core.service.EmailService;
import scesi.org.check.core.service.TemplateService;
import scesi.org.check.latefee.model.event.LateFeePaidEvent;
import scesi.org.check.latefee.model.projection.ILateFeeNotificationProjection;
import scesi.org.check.notification.model.exception.NotificationLateFeeListenerException;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class NotificationLateFeePaidListener {
    private final EmailService emailService;
    private final TemplateService templateService;

    public NotificationLateFeePaidListener(EmailService emailService, TemplateService templateService) {
        this.emailService = emailService;
        this.templateService = templateService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onLateFeePaid(LateFeePaidEvent event) {
        for (ILateFeeNotificationProjection lateFee : event.notif()) {
            try {
                Map<String, Object> data = Map.of(
                        "name", lateFee.getName(),
                        "lastName", lateFee.getLastName(),
                        "event", lateFee.getEvent(),
                        "dateAttendance", formatDate(lateFee.getAttendanceDate()),
                        "amount", lateFee.getAmountFee()
                );
                String notification = templateService.getTemplate("email/html/late-fee-payment", data);
                emailService.sendHtmlMessage(lateFee.getEmail(), "Pago de multa confirmado", notification);
            } catch (MessagingException e) {
                throw new NotificationLateFeeListenerException(e);
            }
        }
    }

    private String formatDate(Instant instant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .format(instant);
    }
}
package scesi.org.check.latefee.model.projection;

import java.math.BigDecimal;
import java.time.Instant;

public interface ILateFeeNotificationProjection {
    String getName();
    String getLastName();
    String getEmail();
    String getEvent();
    Instant getAttendanceDate();
    BigDecimal getAmountFee();
}

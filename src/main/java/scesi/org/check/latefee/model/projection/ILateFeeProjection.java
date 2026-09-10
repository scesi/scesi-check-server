package scesi.org.check.latefee.model.projection;

import java.math.BigDecimal;
import java.time.Instant;

public interface ILateFeeProjection {
    Long getId();

    BigDecimal getAmountFee();

    Instant getCreatedDate();

    Long getAttendanceId();

    String getTypeLateFee();
}

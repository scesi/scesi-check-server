package scesi.org.check.latefee.model.projection;

import java.math.BigDecimal;
import java.time.Instant;

public interface ILateFeeReportProjection {
    String getName();

    String getLastName();

    String getEvent();

    String getTypeAttendance();

    Instant getAttendanceDate();

    BigDecimal getAmountFee();
}

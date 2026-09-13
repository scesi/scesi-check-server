package scesi.org.check.latefee.model.output;

import java.time.YearMonth;

public record LateFeeReportOutput(
        byte[] content,
        YearMonth period) {
}

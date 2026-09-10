package scesi.org.check.latefee.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LateFeeResponse {
    private Long id;
    private BigDecimal amountFee;
    private Instant createdDate;
    private String typeLateFeeEntity;
    private Long attendanceId;
}

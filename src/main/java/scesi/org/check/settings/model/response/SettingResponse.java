package scesi.org.check.settings.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingResponse {
    private Long id;
    private BigDecimal absenceCost;
    private BigDecimal lateArrivalCost;
    private Integer toleranceTimeMinutes;
    private Integer absenceThresholdMinutes;
    private Instant lastLateFeeGenerationDate;
}
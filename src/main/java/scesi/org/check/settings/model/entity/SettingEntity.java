package scesi.org.check.settings.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "setting")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SettingEntity {
    @Id
    private long id;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal absenceCost;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal lateArrivalCost;

    @Column(nullable = false)
    private int toleranceTimeMinutes;

    @Column(nullable = false)
    private int absenceThresholdMinutes;

    @Column(nullable = false)
    private Instant lastLateFeeGenerationDate;
}

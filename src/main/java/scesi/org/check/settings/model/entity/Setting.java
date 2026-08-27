package scesi.org.check.settings.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Setting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "absence_cost")
    private BigDecimal absenceCost;
    @Column(name = "late_arrival_cost")
    private BigDecimal lateArrivalCost;

    private int toleranceTime;
    private int absenceThreshold;
}

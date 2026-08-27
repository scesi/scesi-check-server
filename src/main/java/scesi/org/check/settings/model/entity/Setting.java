package scesi.org.check.settings.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "setting")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal absenceCost;
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal lateArrivalCost;
    private int toleranceTime;
    private int absenceThreshold;
}

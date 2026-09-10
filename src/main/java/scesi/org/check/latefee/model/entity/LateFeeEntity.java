package scesi.org.check.latefee.model.entity;


import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.attendance.model.entity.AttendanceEntity;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "late_fees")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LateFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amountFee;

    @Column(nullable = false)
    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "type_late_fee_id")
    private TypeLateFeeEntity typeLateFeeEntity;

    @OneToOne
    @JoinColumn(name = "attendance_id")
    private AttendanceEntity attendanceEntity;

    @PrePersist
    public void prePersistentEntity() {
        this.createdDate = Instant.now();
    }
}

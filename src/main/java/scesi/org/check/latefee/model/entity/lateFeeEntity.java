package scesi.org.check.latefee.model.entity;


import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.attendance.model.entity.AttendanceEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "late_fees")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class lateFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amountFee;

    @ManyToOne
    @JoinColumn(name = "type_late_fee_id")
    private TypeLateFeeEntity typeLateFeeEntity;

    @OneToOne
    @JoinColumn(name = "attendance_id")
    private AttendanceEntity attendanceEntity;
}

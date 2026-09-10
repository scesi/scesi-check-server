package scesi.org.check.latefee.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "types_late_fee")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeLateFeeEntity {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String typeLateFee;
}

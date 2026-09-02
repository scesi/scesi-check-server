package scesi.org.check.attendance.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_attendaces")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeAttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}

package scesi.org.check.event.model.entity;

import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.attendance.model.entity.AttendanceEntity;

import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "event")
    private List<AttendanceEntity> attendanceList;
}

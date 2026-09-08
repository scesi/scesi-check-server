package scesi.org.check.event.model.entity;

import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.attendance.model.entity.AttendanceEntity;

import java.time.Instant;
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

    @Column(length = 50, nullable = false, unique = true)
    private String title;

    private String description;

    private Instant nextControl;

    @Column(nullable = false)
    private Instant startTime;

    private Instant endTime;

    @OneToMany(mappedBy = "event")
    private List<AttendanceEntity> attendanceList;
}

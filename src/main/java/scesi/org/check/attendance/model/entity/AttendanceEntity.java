package scesi.org.check.attendance.model.entity;

import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.user.model.entity.User;

import java.time.Instant;

@Entity
@Table(name = "attendances",
        uniqueConstraints = @UniqueConstraint(
                columnNames =
        ))
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Instant creationDate;

    @Column(nullable = false)
    private String readerAccuracy;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @ManyToOne
    @JoinColumn(name = "type_attendance_id", nullable = false)
    private TypeAttendanceEntity typeAttendance;

    @PrePersist
    public void prePersistentEntity() {
        this.creationDate = Instant.now();
    }
}

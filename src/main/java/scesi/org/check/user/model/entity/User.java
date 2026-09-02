package scesi.org.check.user.model.entity;

import lombok.*;
import jakarta.persistence.*;
import scesi.org.check.attendance.model.entity.AttendanceEntity;

import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "user")
    private List<RolUser> rolUser;

    @OneToMany(mappedBy = "user")
    private List<AttendanceEntity> attendanceList;

    @PrePersist
    public void prePersistEntity() {
        this.active = true;
    }
}

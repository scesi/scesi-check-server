package scesi.org.check.user.model.entity;

import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.rol.model.entity.Rol;

import java.time.Instant;

@Entity
@Table(
        name = "rol_users",
        uniqueConstraints =
        @UniqueConstraint(
                columnNames = {"user_id", "rol_id"}
        )
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Instant creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @PrePersist
    public void prePersistentEntity() {
        this.creationDate = Instant.now();
    }
}

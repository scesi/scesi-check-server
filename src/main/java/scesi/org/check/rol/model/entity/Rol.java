package scesi.org.check.rol.model.entity;

import jakarta.persistence.*;
import lombok.*;
import scesi.org.check.user.model.entity.RolUser;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String rol;

    @OneToMany(mappedBy = "rol")
    private List<RolUser> rolUser;
}

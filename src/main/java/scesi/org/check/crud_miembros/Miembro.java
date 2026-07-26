package scesi.org.check.crud_miembros;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "users")
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "email")
    private String email;

    @Column(name = "activo")
    private Boolean activo;

}

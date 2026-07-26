package scesi.org.check.crud_miembros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMiembroRepository extends JpaRepository<Miembro, Integer> {
}

package scesi.org.check.rol.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scesi.org.check.rol.model.entity.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol,Long> {

}

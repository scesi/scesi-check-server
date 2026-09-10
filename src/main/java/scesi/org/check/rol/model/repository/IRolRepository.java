package scesi.org.check.rol.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scesi.org.check.rol.model.entity.RolEntity;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<RolEntity,Long> {
    Optional<RolEntity> findByRol(String rol);
}

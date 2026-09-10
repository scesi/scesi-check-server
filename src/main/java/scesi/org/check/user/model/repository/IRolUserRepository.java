package scesi.org.check.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import scesi.org.check.user.model.entity.RolUserEntity;
import scesi.org.check.user.model.projection.IRolesOfUserProjection;


import java.util.List;
import java.util.Optional;

public interface IRolUserRepository extends JpaRepository<RolUserEntity, Long> {
    Optional<RolUserEntity> findByRolIdAndUserId(Long rolId, Long userId);

    @Query("""
                SELECT r.rol, ru.creationDate
                FROM RolEntity r, RolUserEntity ru
                WHERE r = ru.rol AND ru.user.id = :userId
            """)
    List<IRolesOfUserProjection> findAllRolesByUserId(@Param("userId") Long userId);
}

package scesi.org.check.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import scesi.org.check.user.model.dto.RolesOfUserDTO;
import scesi.org.check.user.model.entity.RolUser;


import java.util.List;
import java.util.Optional;

public interface IRolUserRepository extends JpaRepository<RolUser, Long> {
    Optional<RolUser> findByRolIdAndUserId(Long rolId, Long userId);

    @Query("""
                SELECT new scesi.org.check.user.model.dto.RolesOfUserDTO(r.rol, ru.creationDate)
                FROM Rol r, RolUser ru
                WHERE r = ru.rol AND ru.user.id = :userId
            """)
    List<RolesOfUserDTO> findAllRolesByUserId(@Param("userId") Long userId);
}

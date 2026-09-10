package scesi.org.check.latefee.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.latefee.model.entity.LateFeeEntity;

public interface ILateFeeRepository extends JpaRepository<LateFeeEntity, Long> {
}

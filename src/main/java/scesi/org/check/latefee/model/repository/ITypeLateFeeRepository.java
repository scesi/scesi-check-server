package scesi.org.check.latefee.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.latefee.model.entity.TypeLateFeeEntity;

public interface ITypeLateFeeRepository extends JpaRepository<TypeLateFeeEntity, Long> {
}

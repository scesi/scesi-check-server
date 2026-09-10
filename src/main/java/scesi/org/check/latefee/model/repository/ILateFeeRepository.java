package scesi.org.check.latefee.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;

import java.util.List;

public interface ILateFeeRepository extends JpaRepository<LateFeeEntity, Long> {
    @Query("""
                    SELECT lf.id, lf.amountFee, lf.createdDate, lf.attendanceEntity.id as attendanceId, tlf.typeLateFee
                    FROM LateFeeEntity lf, TypeLateFeeEntity tlf
                    WHERE lf.typeLateFeeEntity.id = tlf.id
            """)
    List<ILateFeeProjection> findAllLateFees();
}

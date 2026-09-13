package scesi.org.check.latefee.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.projection.ILateFeeReportProjection;

import java.util.List;

public interface ILateFeeRepository extends JpaRepository<LateFeeEntity, Long> {
    @Query("""
                    SELECT lf.id, lf.amountFee, lf.createdDate, lf.attendanceEntity.id as attendanceId, tlf.typeLateFee
                    FROM LateFeeEntity lf, TypeLateFeeEntity tlf
                    WHERE lf.typeLateFeeEntity.id = tlf.id
            """)
    List<ILateFeeProjection> findAllLateFees();

    @Query("""
            SELECT
                us.name,
                us.lastName,
                evt.title as event,
                tyatt.typeAttendance,
                att.creationDate as attendanceDate,
                lf.amountFee
            FROM LateFeeEntity lf
            JOIN lf.attendanceEntity att
            JOIN att.user us
            JOIN att.event evt
            JOIN att.typeAttendance tyatt
            WHERE lf.typeLateFeeEntity.id = 2
                        order by att.creationDate
            """)
    List<ILateFeeReportProjection> findAllLateFeeReport();
}

package scesi.org.check.attendance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;

import java.time.Instant;
import java.util.List;

public interface IAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findAllByTypeAttendanceAndCreationDateAfter(TypeAttendanceEntity typeAttendance,
                                                                       Instant creationDateAfter);
}

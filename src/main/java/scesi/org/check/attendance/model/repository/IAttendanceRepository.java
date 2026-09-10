package scesi.org.check.attendance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.attendance.model.entity.AttendanceEntity;

public interface IAttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
}

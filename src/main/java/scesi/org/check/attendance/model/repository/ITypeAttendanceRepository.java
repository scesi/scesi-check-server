package scesi.org.check.attendance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;

public interface ITypeAttendanceRepository extends JpaRepository<TypeAttendanceEntity, Long> {
}

package scesi.org.check.attendance.service.rule;

import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.event.model.entity.EventEntity;

import java.util.List;

public record AttendanceContext(
        EventEntity event,
        List<AttendanceEntity> attendanceEntities
) {
}

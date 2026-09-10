package scesi.org.check.attendance.service.rule;

import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.settings.model.entity.Setting;

import java.util.List;

public record AttendanceContext(
        EventEntity event,
        Setting setting,
        List<AttendanceEntity> attendanceEntities
) {
}

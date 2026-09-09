package scesi.org.check.attendance.service.csv;

import scesi.org.check.attendance.model.enumerate.TypeAttendanceEnum;

import java.time.Instant;

public record AttendanceCsvRow(
        TypeAttendanceEnum id,
        Instant creationDate,
        String readerAccuracy
) {
}

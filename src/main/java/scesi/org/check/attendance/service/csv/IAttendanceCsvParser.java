package scesi.org.check.attendance.service.csv;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAttendanceCsvParser {
    List<AttendanceCsvRow> processCsvFile(MultipartFile file);
}

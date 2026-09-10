package scesi.org.check.attendance.service;

import org.springframework.web.multipart.MultipartFile;

public interface IAttendanceService {
    Boolean saveAttendancesFromCSV(MultipartFile file);
}

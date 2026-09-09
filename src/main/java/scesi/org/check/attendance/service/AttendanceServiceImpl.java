package scesi.org.check.attendance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.repository.IAttendanceRepository;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;
import scesi.org.check.attendance.service.csv.AttendanceCsvRow;
import scesi.org.check.attendance.service.csv.IAttendanceCsvParser;
import scesi.org.check.attendance.service.rule.AttendanceContext;
import scesi.org.check.attendance.service.rule.IAttendanceRule;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.exception.EventNotFoundException;
import scesi.org.check.event.model.repository.IEventRepository;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

    private final IAttendanceRepository iAttendanceRepository;
    private final IUserRepository iUserRepository;
    private final IEventRepository iEventRepository;
    private final IAttendanceCsvParser iAttendanceCsvParser;
    private final List<IAttendanceRule> iAttendanceRules;

    public AttendanceServiceImpl(IAttendanceRepository iAttendanceRepository,
                                 IUserRepository iUserRepository,
                                 IEventRepository iEventRepository, ITypeAttendanceRepository iTypeAttendanceRepository, IAttendanceCsvParser iAttendanceCsvParser, List<IAttendanceRule> iAttendanceRule) {
        this.iAttendanceRepository = iAttendanceRepository;
        this.iUserRepository = iUserRepository;
        this.iEventRepository = iEventRepository;
        this.iAttendanceCsvParser = iAttendanceCsvParser;
        this.iAttendanceRules = iAttendanceRule;
    }

    @Override
    public Boolean saveAttendancesFromCSV(MultipartFile file) {
        List<AttendanceCsvRow> attendanceCsvRows = iAttendanceCsvParser.processCsvFile(file);
        List<AttendanceEntity> attendanceEntities = new ArrayList<>();
        for (AttendanceCsvRow attendanceCsvRow : attendanceCsvRows) {
            User user = iUserRepository.getReferenceById(attendanceCsvRow.getIdUser());
            attendanceEntities.add(AttendanceEntity.builder()
                    .creationDate(attendanceCsvRow.getCreationDate())
                    .readerAccuracy(attendanceCsvRow.getReaderAccuracy())
                    .user(user)
                    .build());
        }

        EventEntity event = iEventRepository.findFirstByOrderByStartTimeDesc()
                .orElseThrow(EventNotFoundException::new);
        AttendanceContext attendanceContext = new AttendanceContext(event, attendanceEntities);
        for (IAttendanceRule iAttendanceRule : iAttendanceRules) {
            iAttendanceRule.apply(attendanceContext);
        }
        iAttendanceRepository.saveAll(attendanceEntities);
        return true;
    }
}
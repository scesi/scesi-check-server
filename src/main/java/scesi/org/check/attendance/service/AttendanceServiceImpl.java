package scesi.org.check.attendance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.repository.IAttendanceRepository;
import scesi.org.check.attendance.service.csv.AttendanceCsvRow;
import scesi.org.check.attendance.service.csv.IAttendanceCsvParser;
import scesi.org.check.attendance.service.rule.AttendanceContext;
import scesi.org.check.attendance.service.rule.IAttendanceRule;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.exception.EventNotFoundException;
import scesi.org.check.event.model.repository.IEventRepository;
import scesi.org.check.settings.model.entity.Setting;
import scesi.org.check.settings.model.exception.SettingNotFoundException;
import scesi.org.check.settings.model.repository.ISettingsRepository;
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
    private final ISettingsRepository iSettingsRepository;

    public AttendanceServiceImpl(IAttendanceRepository iAttendanceRepository,
                                 IUserRepository iUserRepository,
                                 IEventRepository iEventRepository,
                                 IAttendanceCsvParser iAttendanceCsvParser,
                                 List<IAttendanceRule> iAttendanceRule, ISettingsRepository iSettingsRepository) {
        this.iAttendanceRepository = iAttendanceRepository;
        this.iUserRepository = iUserRepository;
        this.iEventRepository = iEventRepository;
        this.iAttendanceCsvParser = iAttendanceCsvParser;
        this.iAttendanceRules = iAttendanceRule;
        this.iSettingsRepository = iSettingsRepository;
    }

    @Override
    @Transactional
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
        Setting setting = iSettingsRepository.findById(1L)
                .orElseThrow(SettingNotFoundException::new);
        AttendanceContext attendanceContext = new AttendanceContext(event,setting, attendanceEntities);
        for (IAttendanceRule iAttendanceRule : iAttendanceRules) {
            iAttendanceRule.apply(attendanceContext);
        }
        for(AttendanceEntity attendance : attendanceEntities) attendance.setEvent(event);
        iAttendanceRepository.saveAll(attendanceEntities);
        return true;
    }
}
package scesi.org.check.attendance.service.rule;

import org.springframework.stereotype.Component;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;
import scesi.org.check.attendance.model.enumerate.TypeAttendanceEnum;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.repository.IUserRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class AbsenceAttendanceRule implements IAttendanceRule {
    private final ITypeAttendanceRepository iTypeAttendanceRepository;
    private final IUserRepository iUserRepository;

    public AbsenceAttendanceRule(ITypeAttendanceRepository iTypeAttendanceRepository, IUserRepository iUserRepository) {
        this.iTypeAttendanceRepository = iTypeAttendanceRepository;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public void apply(AttendanceContext context) {
        TypeAttendanceEntity absence = iTypeAttendanceRepository.getReferenceById(
                TypeAttendanceEnum
                        .ABSENCE
                        .getId());
        List<Long> idUser = new ArrayList<>();
        for(AttendanceEntity attendance: context.attendanceEntities()){
            Instant attendanceTime = attendance.getCreationDate();
            Instant eventTime = context.event().getStartTime();
            Instant absenceTime = eventTime.plus(Duration.ofMinutes(context.settingEntity().getAbsenceThresholdMinutes()));
            idUser.add(attendance.getUser().getId());
            if(attendanceTime.isAfter(absenceTime) || attendanceTime.equals(absenceTime)){
                attendance.setTypeAttendance(absence);
            }
        }
        List<User> usersAbsence = iUserRepository.findByIdNotInList(idUser);
        for(User user : usersAbsence){
            context.attendanceEntities().add(AttendanceEntity.builder().user(user).typeAttendance(absence).build());
        }
    }
}

package scesi.org.check.attendance.service.rule;

import org.springframework.stereotype.Component;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;
import scesi.org.check.attendance.model.enumerate.TypeAttendanceEnum;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;

import java.time.Duration;
import java.time.Instant;

@Component
public class PresentAttendanceRule implements IAttendanceRule{
    private final ITypeAttendanceRepository iTypeAttendanceRepository;

    public PresentAttendanceRule(ITypeAttendanceRepository iTypeAttendanceRepository) {
        this.iTypeAttendanceRepository = iTypeAttendanceRepository;
    }

    @Override
    public void apply(AttendanceContext context) {
        TypeAttendanceEntity present = iTypeAttendanceRepository.getReferenceById(TypeAttendanceEnum.PRESENT.getId());
        for(AttendanceEntity attendance: context.attendanceEntities()){
            Instant attendanceTime = attendance.getCreationDate();
            Instant toleranceTime = context.event()
                    .getStartTime()
                    .plus(Duration.ofMinutes(context.setting().getToleranceTimeMinutes()));

            if(attendanceTime.isBefore(toleranceTime)){
                attendance.setTypeAttendance(present);
            }
        }
    }
}

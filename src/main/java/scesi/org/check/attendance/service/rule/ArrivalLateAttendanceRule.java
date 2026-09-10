package scesi.org.check.attendance.service.rule;

import org.springframework.stereotype.Component;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;
import scesi.org.check.attendance.model.enumerate.TypeAttendanceEnum;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;

import java.time.Duration;
import java.time.Instant;

@Component
public class ArrivalLateAttendanceRule implements IAttendanceRule {
    private final ITypeAttendanceRepository iTypeAttendanceRepository;

    public ArrivalLateAttendanceRule(ITypeAttendanceRepository iTypeAttendanceRepository) {
        this.iTypeAttendanceRepository = iTypeAttendanceRepository;
    }

    @Override
    public void apply(AttendanceContext context) {
        TypeAttendanceEntity lateArrival = iTypeAttendanceRepository.getReferenceById(
                TypeAttendanceEnum
                .LATE_ARRIVAL
                .getId());

        for(AttendanceEntity attendance: context.attendanceEntities()){
            Instant attendanceTime = attendance.getCreationDate();
            Instant eventTime = context.event().getStartTime();
            Instant absenceTime = eventTime.plus(Duration.ofMinutes(context.settingEntity().getAbsenceThresholdMinutes()));
            Instant toleranceTime = eventTime.plus(Duration.ofMinutes(context.settingEntity().getToleranceTimeMinutes()));

            if((attendanceTime.isAfter(toleranceTime) || attendanceTime.equals(toleranceTime))
                && attendanceTime.isBefore(absenceTime)){
                attendance.setTypeAttendance(lateArrival);
            }
        }
    }
}

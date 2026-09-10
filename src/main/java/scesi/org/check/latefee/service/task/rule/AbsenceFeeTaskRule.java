package scesi.org.check.latefee.service.task.rule;

import org.springframework.stereotype.Component;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;
import scesi.org.check.attendance.model.enumerate.TypeAttendanceEnum;
import scesi.org.check.attendance.model.repository.IAttendanceRepository;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;
import scesi.org.check.latefee.model.entity.LateFeeEntity;

import java.util.List;

@Component
public class AbsenceFeeTaskRule implements ILateFeeTaskRule {
    private final ITypeAttendanceRepository iTypeAttendanceRepository;
    private final IAttendanceRepository iAttendanceRepository;

    public AbsenceFeeTaskRule(ITypeAttendanceRepository iTypeAttendanceRepository,
                              IAttendanceRepository iAttendanceRepository) {
        this.iTypeAttendanceRepository = iTypeAttendanceRepository;
        this.iAttendanceRepository = iAttendanceRepository;
    }

    @Override
    public void apply(LateFeeTaskContext context) {
        TypeAttendanceEntity absenceType = iTypeAttendanceRepository.getReferenceById(TypeAttendanceEnum.ABSENCE.getId());
        List<AttendanceEntity> absenceAttendances = iAttendanceRepository
                .findAllByTypeAttendanceAndCreationDateAfter(
                        absenceType,
                        context.settings().getLastLateFeeGenerationDate()
                );
        for (AttendanceEntity attendance : absenceAttendances) {
            context.lateFeeEntities().add(LateFeeEntity.builder()
                    .amountFee(context.settings().getAbsenceCost())
                    .attendanceEntity(attendance)
                    .build());
        }
    }
}

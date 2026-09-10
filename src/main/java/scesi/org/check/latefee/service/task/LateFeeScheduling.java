package scesi.org.check.latefee.service.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import scesi.org.check.attendance.model.entity.AttendanceEntity;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;
import scesi.org.check.attendance.model.enumerate.TypeAttendanceEnum;
import scesi.org.check.attendance.model.repository.IAttendanceRepository;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.entity.TypeLateFeeEntity;
import scesi.org.check.latefee.model.repository.ILateFeeRepository;
import scesi.org.check.latefee.model.repository.ITypeLateFeeRepository;
import scesi.org.check.settings.model.entity.Setting;
import scesi.org.check.settings.model.exception.SettingNotFoundException;
import scesi.org.check.settings.model.repository.ISettingsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class LateFeeScheduling {
    private final IAttendanceRepository iAttendanceRepository;
    private final ITypeAttendanceRepository iTypeAttendanceRepository;
    private final ISettingsRepository iSettingsRepository;
    private final ITypeLateFeeRepository iTypeLateFeeRepository;
    private final ILateFeeRepository iLateFeeRepository;

    public LateFeeScheduling(IAttendanceRepository iAttendanceRepository,
                             ITypeAttendanceRepository iTypeAttendanceRepository,
                             ISettingsRepository iSettingsRepository,
                             ITypeLateFeeRepository iTypeLateFeeRepository, ILateFeeRepository iLateFeeRepository) {
        this.iAttendanceRepository = iAttendanceRepository;
        this.iTypeAttendanceRepository = iTypeAttendanceRepository;
        this.iSettingsRepository = iSettingsRepository;
        this.iTypeLateFeeRepository = iTypeLateFeeRepository;
        this.iLateFeeRepository = iLateFeeRepository;
    }

    @Transactional
    @Scheduled(cron = "@monthly")
    public void generateMonthlyLateFees() {
        TypeAttendanceEntity lateArrivalType = iTypeAttendanceRepository.getReferenceById(TypeAttendanceEnum.LATE_ARRIVAL.getId());
        TypeAttendanceEntity absenceType = iTypeAttendanceRepository.getReferenceById(TypeAttendanceEnum.ABSENCE.getId());
        TypeLateFeeEntity notPayedType = iTypeLateFeeRepository.getReferenceById(2L);
        Setting settings = iSettingsRepository.findById(1L).orElseThrow(SettingNotFoundException::new);
        List<AttendanceEntity> lateArrivalAttendances = iAttendanceRepository.findAllByTypeAttendanceAndCreationDateAfter(lateArrivalType, settings.getLastLateFeeGenerationDate());
        List<AttendanceEntity> absenceAttendances = iAttendanceRepository.findAllByTypeAttendanceAndCreationDateAfter(absenceType, settings.getLastLateFeeGenerationDate());
        List<LateFeeEntity> lateFeeToGenerate = new ArrayList<>();
        for (AttendanceEntity attendance : lateArrivalAttendances) {
            lateFeeToGenerate.add(LateFeeEntity.builder()
                    .amountFee(settings.getLateArrivalCost())
                    .typeLateFeeEntity(notPayedType)
                    .attendanceEntity(attendance)
                    .build());
        }
        for (AttendanceEntity attendance : absenceAttendances) {
            lateFeeToGenerate.add(LateFeeEntity.builder()
                    .amountFee(settings.getAbsenceCost())
                    .typeLateFeeEntity(notPayedType)
                    .attendanceEntity(attendance)
                    .build());
        }
        iLateFeeRepository.saveAll(lateFeeToGenerate);
    }
}

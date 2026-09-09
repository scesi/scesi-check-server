package scesi.org.check.attendance.model.seed;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import scesi.org.check.attendance.model.entity.TypeAttendanceEntity;
import scesi.org.check.attendance.model.repository.ITypeAttendanceRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeAttendanceSeed implements ApplicationRunner {
    private final ITypeAttendanceRepository iTypeAttendanceRepository;

    public TypeAttendanceSeed(ITypeAttendanceRepository iTypeAttendanceRepository) {
        this.iTypeAttendanceRepository = iTypeAttendanceRepository;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        if (iTypeAttendanceRepository.count() == 0) {
            List<TypeAttendanceEntity> typeAttendanceEntities = new ArrayList<>();
            typeAttendanceEntities.add(TypeAttendanceEntity.builder().id(1L).typeAttendance("Present").build());
            typeAttendanceEntities.add(TypeAttendanceEntity.builder().id(2L).typeAttendance("Late arrival").build());
            typeAttendanceEntities.add(TypeAttendanceEntity.builder().id(3L).typeAttendance("Absence").build());
            iTypeAttendanceRepository.saveAll(typeAttendanceEntities);
        }
    }
}

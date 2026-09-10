package scesi.org.check.settings.config.seed;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import scesi.org.check.settings.model.entity.SettingEntity;
import scesi.org.check.settings.model.repository.ISettingsRepository;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class SettingSeed implements ApplicationRunner {
    private final ISettingsRepository iSettingsRepository;

    public SettingSeed(ISettingsRepository iSettingsRepository) {
        this.iSettingsRepository = iSettingsRepository;
    }


    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (iSettingsRepository.count() == 0) {
            iSettingsRepository.save(SettingEntity.builder()
                    .id(1)
                    .absenceCost(BigDecimal.TWO)
                    .lateArrivalCost(BigDecimal.valueOf(20))
                    .toleranceTimeMinutes(5)
                    .absenceThresholdMinutes(30)
                    .lastLateFeeGenerationDate(Instant.now())
                    .build());
        }
    }
}

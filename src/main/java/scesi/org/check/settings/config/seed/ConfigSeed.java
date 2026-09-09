package scesi.org.check.settings.config.seed;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import scesi.org.check.settings.model.entity.Setting;
import scesi.org.check.settings.model.repository.ISettingsRepository;

import java.math.BigDecimal;

@Component
public class ConfigSeed implements ApplicationRunner {
    private final ISettingsRepository iSettingsRepository;

    public ConfigSeed(ISettingsRepository iSettingsRepository) {
        this.iSettingsRepository = iSettingsRepository;
    }


    @Override
    public void run(@NonNull ApplicationArguments args) throws Exception {
        if(iSettingsRepository.count() == 0){
            iSettingsRepository.save(Setting.builder()
                    .id(1)
                    .absenceCost(BigDecimal.TWO)
                    .lateArrivalCost(BigDecimal.valueOf(20))
                    .toleranceTimeMinutes(5)
                    .absenceThresholdMinutes(30)
                    .build());
        }
    }
}

package scesi.org.check.settings.service;

import org.springframework.stereotype.Service;
import scesi.org.check.settings.model.entity.SettingEntity;
import scesi.org.check.settings.model.exception.SettingNotFoundException;
import scesi.org.check.settings.model.repository.ISettingsRepository;
import scesi.org.check.settings.model.request.UpdateSettingsRequest;

import java.time.Instant;

@Service
public class SettingsServiceImpl implements ISettingsService {

    private final ISettingsRepository iSettingsRepository;

    public SettingsServiceImpl(ISettingsRepository iSettingsRepository) {
        this.iSettingsRepository = iSettingsRepository;
    }

    @Override
    public SettingEntity getSettings() {
        return iSettingsRepository.findById(1L)
                .orElseThrow(SettingNotFoundException::new);
    }

    @Override
    public SettingEntity updateSettings(UpdateSettingsRequest request) {
        SettingEntity settings = iSettingsRepository.findById(1L)
                .orElseThrow(SettingNotFoundException::new);

        settings.setAbsenceCost(request.absenceCost());
        settings.setLateArrivalCost(request.lateArrivalCost());
        settings.setToleranceTimeMinutes(request.toleranceTimeMinutes());
        settings.setAbsenceThresholdMinutes(request.absenceThresholdMinutes());
        settings.setLastLateFeeGenerationDate(Instant.now());

        return iSettingsRepository.save(settings);
    }
}
package scesi.org.check.settings.service;

import scesi.org.check.settings.model.entity.SettingEntity;
import scesi.org.check.settings.model.request.UpdateSettingsRequest;

public interface ISettingsService {
    SettingEntity getSettings();

    SettingEntity updateSettings(UpdateSettingsRequest request);
}
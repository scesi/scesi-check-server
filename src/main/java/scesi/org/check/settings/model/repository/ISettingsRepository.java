package scesi.org.check.settings.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.settings.model.entity.SettingEntity;

public interface ISettingsRepository extends JpaRepository<SettingEntity, Long> {
}

package scesi.org.check.settings.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scesi.org.check.settings.model.entity.Setting;

public interface ISettingsRepository extends JpaRepository<Setting, Long> {
}

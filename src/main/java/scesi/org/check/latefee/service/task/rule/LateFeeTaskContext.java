package scesi.org.check.latefee.service.task.rule;

import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.settings.model.entity.Setting;

import java.util.List;

public record LateFeeTaskContext(
        Setting settings,
        List<LateFeeEntity> lateFeeEntities
) {
}

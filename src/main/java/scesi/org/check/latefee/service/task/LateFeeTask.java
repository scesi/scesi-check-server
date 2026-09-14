package scesi.org.check.latefee.service.task;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.entity.TypeLateFeeEntity;
import scesi.org.check.latefee.model.event.LateFeeGeneratedEvent;
import scesi.org.check.latefee.model.projection.ILateFeeNotificationProjection;
import scesi.org.check.latefee.model.repository.ILateFeeRepository;
import scesi.org.check.latefee.model.repository.ITypeLateFeeRepository;
import scesi.org.check.latefee.service.task.rule.ILateFeeTaskRule;
import scesi.org.check.latefee.service.task.rule.LateFeeTaskContext;
import scesi.org.check.settings.model.entity.SettingEntity;
import scesi.org.check.settings.model.exception.SettingNotFoundException;
import scesi.org.check.settings.model.repository.ISettingsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class LateFeeTask {
    private final ISettingsRepository iSettingsRepository;
    private final ITypeLateFeeRepository iTypeLateFeeRepository;
    private final ILateFeeRepository iLateFeeRepository;
    private final List<ILateFeeTaskRule> iLateFeeTaskRules;
    private final ApplicationEventPublisher applicationEventPublisher;

    public LateFeeTask(ISettingsRepository iSettingsRepository,
                       ITypeLateFeeRepository iTypeLateFeeRepository,
                       ILateFeeRepository iLateFeeRepository,
                       List<ILateFeeTaskRule> iLateFeeTaskRules, ApplicationEventPublisher applicationEventPublisher) {
        this.iSettingsRepository = iSettingsRepository;
        this.iTypeLateFeeRepository = iTypeLateFeeRepository;
        this.iLateFeeRepository = iLateFeeRepository;
        this.iLateFeeTaskRules = iLateFeeTaskRules;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    @Scheduled(cron = "@monthly")
    public void generateMonthlyLateFees() {
        TypeLateFeeEntity notPayedType = iTypeLateFeeRepository.getReferenceById(2L);
        SettingEntity settings = iSettingsRepository.findById(1L).orElseThrow(SettingNotFoundException::new);
        List<LateFeeEntity> lateFeesToGenerate = new ArrayList<>();
        for (ILateFeeTaskRule iLateFeeTaskRule : iLateFeeTaskRules) {
            iLateFeeTaskRule.apply(new LateFeeTaskContext(settings, lateFeesToGenerate));
        }
        for (LateFeeEntity lateFee : lateFeesToGenerate) {
            lateFee.setTypeLateFeeEntity(notPayedType);
        }
        List<LateFeeEntity> lateFeesSaved = iLateFeeRepository.saveAll(lateFeesToGenerate);

        List<Long> feeIds = lateFeesSaved.stream().map(LateFeeEntity::getId).toList();
        List<ILateFeeNotificationProjection> notifications = iLateFeeRepository.findNotificationDataByFeeIds(feeIds);

        applicationEventPublisher.publishEvent(new LateFeeGeneratedEvent(notifications));
    }
}

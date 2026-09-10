package scesi.org.check.latefee.config.seed;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import scesi.org.check.latefee.model.entity.TypeLateFeeEntity;
import scesi.org.check.latefee.model.repository.iTypeLateFeeRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class TypeLateFeeSeed implements ApplicationRunner {

    private final iTypeLateFeeRepository iTypeLateFeeRepository;

    public TypeLateFeeSeed(iTypeLateFeeRepository iTypeLateFeeRepository) {
        this.iTypeLateFeeRepository = iTypeLateFeeRepository;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) {
        if (iTypeLateFeeRepository.count() == 0) {
            List<TypeLateFeeEntity> typeLateFeeEntityList = new ArrayList<>();
            typeLateFeeEntityList.add(TypeLateFeeEntity.builder().id(1L).typeLateFee("Payed").build());
            typeLateFeeEntityList.add(TypeLateFeeEntity.builder().id(2L).typeLateFee("Not Payed").build());
            iTypeLateFeeRepository.saveAll(typeLateFeeEntityList);
        }
    }
}

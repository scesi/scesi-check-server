package scesi.org.check.latefee.service.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scesi.org.check.latefee.model.entity.LateFeeEntity;
import scesi.org.check.latefee.model.entity.TypeLateFeeEntity;
import scesi.org.check.latefee.model.exception.LateFeeNotFoundException;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.repository.ILateFeeRepository;
import scesi.org.check.latefee.model.repository.ITypeLateFeeRepository;
import scesi.org.check.rol.controller.ILateFeeService;

import java.util.List;

@Service
public class LateFeeServiceImpl implements ILateFeeService {
    private final ILateFeeRepository iLateFeeRepository;
    private final ITypeLateFeeRepository iTypeLateFeeRepository;

    public LateFeeServiceImpl(ILateFeeRepository iLateFeeRepository, ITypeLateFeeRepository iTypeLateFeeRepository) {
        this.iLateFeeRepository = iLateFeeRepository;
        this.iTypeLateFeeRepository = iTypeLateFeeRepository;
    }


    @Override
    public List<ILateFeeProjection> getAllLateFees() {
        return iLateFeeRepository.findAllLateFees();
    }

    @Override
    @Transactional
    public Boolean changeTypeLateFeeById(Long id) {
        LateFeeEntity latefeeToChange = iLateFeeRepository.findById(id).orElseThrow(LateFeeNotFoundException::new);
        TypeLateFeeEntity typeLateFee = iTypeLateFeeRepository.getReferenceById(1L);
        latefeeToChange.setTypeLateFeeEntity(typeLateFee);
        iLateFeeRepository.save(latefeeToChange);
        return true;
    }
}

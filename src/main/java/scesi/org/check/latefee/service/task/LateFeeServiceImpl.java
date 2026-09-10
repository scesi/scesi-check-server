package scesi.org.check.latefee.service.task;

import org.springframework.stereotype.Service;
import scesi.org.check.latefee.model.projection.ILateFeeProjection;
import scesi.org.check.latefee.model.repository.ILateFeeRepository;
import scesi.org.check.rol.controller.ILateFeeService;

import java.util.List;

@Service
public class LateFeeServiceImpl implements ILateFeeService {
    private final ILateFeeRepository iLateFeeRepository;

    public LateFeeServiceImpl(ILateFeeRepository iLateFeeRepository) {
        this.iLateFeeRepository = iLateFeeRepository;
    }


    @Override
    public List<ILateFeeProjection> getAllLateFees() {
        return iLateFeeRepository.findAllLateFees();
    }

    @Override
    public Boolean changeTypeLateFeeById(Long id) {
        return null;
    }
}

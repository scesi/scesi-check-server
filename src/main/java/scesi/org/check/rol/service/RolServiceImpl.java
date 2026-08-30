package scesi.org.check.rol.service;

import org.springframework.stereotype.Service;
import scesi.org.check.rol.model.entity.Rol;
import scesi.org.check.rol.model.exception.RolNotFoundException;
import scesi.org.check.rol.model.repository.IRolRepository;
import scesi.org.check.rol.model.request.CreateRolRequest;
import scesi.org.check.rol.model.request.UpdateRolRequest;
import scesi.org.check.user.model.exceptions.RolUserAlreadyExistException;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements IRolService {

    private final IRolRepository iRolRepository;

    public RolServiceImpl(IRolRepository iRolRepository) {
        this.iRolRepository = iRolRepository;
    }

    @Override
    public Rol getRolById(Long rolId) {
        Optional<Rol> rolOptional = iRolRepository.findById(rolId);
        if (rolOptional.isEmpty()) {
            throw new RolNotFoundException();
        }
        return rolOptional.get();
    }

    @Override
    public List<Rol> getAllRoles() {
        return iRolRepository.findAll();
    }

    @Override
    public Boolean deleteRol(Long rolId) {
        Optional<Rol> rolOptional = iRolRepository.findById(rolId);
        if (rolOptional.isEmpty()) {
            throw new RolNotFoundException();
        }
        iRolRepository.delete(rolOptional.get());
        return true;
    }

    @Override
    public Rol createRol(CreateRolRequest request) {
        Optional<Rol> rolOptional = iRolRepository.findByRol(request.rol());
        if (rolOptional.isPresent()) {
            throw new RolUserAlreadyExistException();
        }
        final Rol rol = Rol.builder()
                .rol(request.rol())
                .build();
        return iRolRepository.save(rol);
    }

    @Override
    public Rol updateRol(Long rolId, UpdateRolRequest request) {
        Optional<Rol> rolOptional = iRolRepository.findById(rolId);
        if (rolOptional.isEmpty()) {
            throw new RolNotFoundException();
        }
        Optional<Rol> rolOptionalName = iRolRepository.findByRol(request.rol());
        if (rolOptionalName.isPresent()) {
            throw new RolUserAlreadyExistException();
        }
        Rol rolToUpdate = rolOptional.get();
        if (request.rol() != null) {
            rolToUpdate.setRol(request.rol());
        }
        iRolRepository.save(rolToUpdate);
        return rolToUpdate;
    }
}

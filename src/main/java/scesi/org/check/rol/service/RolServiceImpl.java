package scesi.org.check.rol.service;

import org.springframework.stereotype.Service;
import scesi.org.check.rol.model.entity.Rol;
import scesi.org.check.rol.model.request.CreateRolRequest;
import scesi.org.check.rol.model.request.UpdateRolRequest;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService{
    @Override
    public Rol getRolById(Long id) {
        return null;
    }

    @Override
    public List<Rol> getAllRoles() {
        return List.of();
    }

    @Override
    public Boolean deleteRol(Long id) {
        return null;
    }

    @Override
    public Rol createRol(CreateRolRequest request) {
        return null;
    }

    @Override
    public Rol updateRol(Long rolId, UpdateRolRequest request) {
        return null;
    }
}

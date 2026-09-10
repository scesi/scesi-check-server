package scesi.org.check.rol.service;

import scesi.org.check.rol.model.entity.RolEntity;
import scesi.org.check.rol.model.request.CreateRolRequest;
import scesi.org.check.rol.model.request.UpdateRolRequest;

import java.util.List;

public interface IRolService {
    RolEntity getRolById(Long rolId);
    List<RolEntity> getAllRoles();
    Boolean deleteRol(Long rolId);
    RolEntity createRol(CreateRolRequest request);
    RolEntity updateRol(Long rolId, UpdateRolRequest request);
}

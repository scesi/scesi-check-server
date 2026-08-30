package scesi.org.check.rol.service;

import scesi.org.check.rol.model.entity.Rol;
import scesi.org.check.rol.model.request.CreateRolRequest;
import scesi.org.check.rol.model.request.UpdateRolRequest;

import java.util.List;

public interface IRolService {
    Rol getRolById(Long id);
    List<Rol> getAllRoles();
    Boolean deleteRol(Long id);
    Rol createRol(CreateRolRequest request);
    Rol updateRol(Long rolId, UpdateRolRequest request);
}

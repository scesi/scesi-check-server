package scesi.org.check.user.service;

import scesi.org.check.user.model.dto.RolesOfUserDTO;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.request.CreateUserRequest;
import scesi.org.check.user.model.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    Boolean deleteUser(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(Long id, UpdateUserRequest request);
    Boolean assignRol(Long userId, Long rolId);
    Boolean removeRolAssigned(Long userId, Long rolId);
    List<RolesOfUserDTO> getAllAssignedUserRoles(Long userId);
}

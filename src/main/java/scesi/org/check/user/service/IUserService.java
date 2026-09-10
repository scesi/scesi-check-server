package scesi.org.check.user.service;

import scesi.org.check.user.model.entity.UserEntity;
import scesi.org.check.user.model.projection.IRolesOfUserProjection;
import scesi.org.check.user.model.request.CreateUserRequest;
import scesi.org.check.user.model.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    UserEntity getUserById(Long id);

    List<UserEntity> getAllUsers();

    Boolean deleteUser(Long id);

    UserEntity createUser(CreateUserRequest request);

    UserEntity updateUser(Long id, UpdateUserRequest request);

    Boolean assignRol(Long userId, Long rolId);

    Boolean removeRolAssigned(Long userId, Long rolId);

    List<IRolesOfUserProjection> getAllAssignedUserRoles(Long userId);
}

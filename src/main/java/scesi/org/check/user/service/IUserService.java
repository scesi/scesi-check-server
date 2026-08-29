package scesi.org.check.user.service;

import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.request.CreateUserRequest;
import scesi.org.check.user.model.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long id);
    User getAllUsers();
    Boolean DeleteUser(Long id);
    User CreateUser(CreateUserRequest request);
    User UpdateUser(Long id, UpdateUserRequest request);
    Boolean AssignRol(Long userId, Long rolId);
}

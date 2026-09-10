package scesi.org.check.user.service;

import org.springframework.stereotype.Service;
import scesi.org.check.rol.model.entity.RolEntity;
import scesi.org.check.rol.service.IRolService;
import scesi.org.check.user.model.entity.RolUserEntity;
import scesi.org.check.user.model.entity.UserEntity;
import scesi.org.check.user.model.exceptions.RolUserAlreadyExistException;
import scesi.org.check.user.model.exceptions.RolUserNotFoundException;
import scesi.org.check.user.model.exceptions.UserEmailAlreadyExistException;
import scesi.org.check.user.model.exceptions.UserNotFoundException;
import scesi.org.check.user.model.projection.IRolesOfUserProjection;
import scesi.org.check.user.model.repository.IRolUserRepository;
import scesi.org.check.user.model.repository.IUserRepository;
import scesi.org.check.user.model.request.CreateUserRequest;
import scesi.org.check.user.model.request.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final IRolService iRolService;
    private final IRolUserRepository iRolUserRepository;

    public UserServiceImpl(IUserRepository iUserRepository, IRolService iRolService, IRolUserRepository iRolUserRepository) {
        this.iUserRepository = iUserRepository;
        this.iRolService = iRolService;
        this.iRolUserRepository = iRolUserRepository;
    }


    @Override
    public UserEntity getUserById(Long id) {
        Optional<UserEntity> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userOptional.get();
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<UserEntity> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        iUserRepository.delete(userOptional.get());
        return true;
    }

    @Override
    public UserEntity createUser(CreateUserRequest request) {
        Optional<UserEntity> userOptionalEmailVerification = iUserRepository.findByEmail(request.email());
        if (userOptionalEmailVerification.isPresent()) {
            throw new UserEmailAlreadyExistException();
        }
        final UserEntity user = UserEntity.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        return iUserRepository.save(user);
    }

    @Override
    public UserEntity updateUser(Long id, UpdateUserRequest request) {
        Optional<UserEntity> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserEntity userToUpdate = userOptional.get();
        if (request.active() != null) {
            userToUpdate.setActive(request.active());
        }
        if (request.email() != null) {
            Optional<UserEntity> userOptionalEmailVerification = iUserRepository.findByEmail(request.email());
            if (userOptionalEmailVerification.isPresent()) {
                throw new UserEmailAlreadyExistException();
            }
            userToUpdate.setEmail(request.email());
        }
        if (request.lastName() != null) {
            userToUpdate.setLastName(request.lastName());
        }
        if (request.name() != null) {
            userToUpdate.setName(request.name());
        }
        iUserRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public Boolean assignRol(Long userId, Long rolId) {
        RolEntity rol = iRolService.getRolById(rolId);
        Optional<UserEntity> userOptional = iUserRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserEntity user = userOptional.get();
        Optional<RolUserEntity> rolUserOptional = iRolUserRepository.findByRolIdAndUserId(rolId, userId);
        if (rolUserOptional.isPresent()) {
            throw new RolUserAlreadyExistException();
        }
        RolUserEntity rolUser = RolUserEntity.builder()
                .user(user)
                .rol(rol)
                .build();
        iRolUserRepository.save(rolUser);
        return true;
    }

    @Override
    public Boolean removeRolAssigned(Long userId, Long rolId) {
        Optional<RolUserEntity> rolUserOptional = iRolUserRepository.findByRolIdAndUserId(rolId, userId);
        if (rolUserOptional.isEmpty()) {
            throw new RolUserNotFoundException();
        }
        iRolUserRepository.delete(rolUserOptional.get());
        return true;
    }

    @Override
    public List<IRolesOfUserProjection> getAllAssignedUserRoles(Long userId) {
        return iRolUserRepository.findAllRolesByUserId(userId);
    }
}

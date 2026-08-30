package scesi.org.check.user.service;

import org.springframework.stereotype.Service;
import scesi.org.check.rol.model.entity.Rol;
import scesi.org.check.rol.service.IRolService;
import scesi.org.check.user.model.dto.RolesOfUserDTO;
import scesi.org.check.user.model.entity.RolUser;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.exceptions.RolUserAlreadyExistException;
import scesi.org.check.user.model.exceptions.RolUserNotFoundException;
import scesi.org.check.user.model.exceptions.UserNotFoundException;
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
    public User getUserById(Long id) {
        Optional<User> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<User> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        iUserRepository.delete(userOptional.get());
        return true;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        final User user = User.builder()
                .name(request.name())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        return iUserRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        Optional<User> userOptional = iUserRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User userToUpdate = userOptional.get();
        if (request.active() != null) {
            userToUpdate.setActive(request.active());
        }
        if (request.email() != null) {
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

    // TODO: create exception for unique constraints
    @Override
    public Boolean assignRol(Long userId, Long rolId) {
        Rol rol = iRolService.getRolById(rolId);
        Optional<User> userOptional = iUserRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userOptional.get();
        Optional<RolUser> rolUserOptional = iRolUserRepository.findByRolIdAndUserId(rolId, userId);
        if(rolUserOptional.isPresent()){
            throw new RolUserAlreadyExistException();
        }
        RolUser rolUser = RolUser.builder()
                .user(user)
                .rol(rol)
                .build();
        iRolUserRepository.save(rolUser);
        return true;
    }

    @Override
    public Boolean removeRolAssigned(Long userId, Long rolId) {
        Optional<RolUser> rolUserOptional = iRolUserRepository.findByRolIdAndUserId(rolId, userId);
        if(rolUserOptional.isEmpty()){
            throw new RolUserNotFoundException();
        }
        iRolUserRepository.delete(rolUserOptional.get());
        return true;
    }

    @Override
    public List<RolesOfUserDTO> getAllAssignedUserRoles(Long userId) {
        return iRolUserRepository.findAllRolesByUserId(userId);
    }
}

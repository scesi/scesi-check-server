package scesi.org.check.user.service;

import org.springframework.stereotype.Service;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.exceptions.UserNotFoundException;
import scesi.org.check.user.model.repository.IUserRepository;
import scesi.org.check.user.model.request.CreateUserRequest;
import scesi.org.check.user.model.request.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;

    public UserServiceImpl(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
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

    // TODO: create exception to unique constraints
    @Override
    public Boolean assignRol(Long userId, Long rolId) {
        // TODO: when Rol module is implemented
        return null;
    }
}

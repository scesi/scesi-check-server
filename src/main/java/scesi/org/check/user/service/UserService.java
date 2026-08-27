package scesi.org.check.user.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.repository.IUserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    public User saveUser(User user) {
        if(user.getId() == null) {
            return iUserRepository.save(user);
        }
        return null;
    }

    public Optional<User> getUserById(int id) {
        return iUserRepository.findById(id);
    }

    public User editUser(User user) {
        if(user.getId() != null && iUserRepository.existsById(user.getId())) {
            return iUserRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(int id) {
        if (!iUserRepository.existsById(id)) {
            return false;
        }

        iUserRepository.deleteById(id);
        return true;
    }
}

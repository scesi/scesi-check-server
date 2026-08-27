package scesi.org.check.user.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import scesi.org.check.user.model.entity.User;
import scesi.org.check.user.model.repository.IUserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository imiembroRepository;

    public User saveMiembro(User user) {
        if(user.getId() == null) {
            return imiembroRepository.save(user);
        }
        return null;
    }

    public Optional<User> getMiembroById(int id) {
        return imiembroRepository.findById(id);
    }

    public User editMiembro(User user) {
        if(user.getId() != null && imiembroRepository.existsById(user.getId())) {
            return imiembroRepository.save(user);
        }
        return null;
    }

    public boolean deleteMiembro(int id) {
        if (!imiembroRepository.existsById(id)) {
            return false;
        }

        imiembroRepository.deleteById(id);
        return true;
    }
}

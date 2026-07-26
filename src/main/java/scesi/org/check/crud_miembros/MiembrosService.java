package scesi.org.check.crud_miembros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class MiembrosService {

    @Autowired
    private IMiembroRepository imiembroRepository;

    public Miembro saveMiembro(Miembro miembro) {
        if(miembro.getId() == null) {
            return imiembroRepository.save(miembro);
        }
        return null;
    }

    public Optional<Miembro> getMiembroById(int id) {
        return imiembroRepository.findById(id);
    }

    public Miembro editMiembro(Miembro miembro) {
        if(miembro.getId() != null && imiembroRepository.existsById(miembro.getId())) {
            return imiembroRepository.save(miembro);
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

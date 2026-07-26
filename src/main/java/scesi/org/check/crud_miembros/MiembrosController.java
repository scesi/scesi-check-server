package scesi.org.check.crud_miembros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/miembro")
public class MiembrosController {
    @Autowired
    private MiembrosService miembrosService;

    @PostMapping
    public ResponseEntity<Miembro> saveMiembro (@RequestBody Miembro miembro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(miembrosService.saveMiembro(miembro));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Integer id) {
        if (!miembrosService.deleteMiembro(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Miembro>> getMiembroById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(miembrosService.getMiembroById(id));
    }

    @PutMapping
    public ResponseEntity<Miembro> editMiembro(@Valid @RequestBody Miembro miembro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(miembrosService.editMiembro(miembro));
    }

}

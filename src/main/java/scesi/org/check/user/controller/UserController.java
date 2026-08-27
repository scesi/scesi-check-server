package scesi.org.check.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import scesi.org.check.user.service.UserService;
import scesi.org.check.user.model.entity.User;

import java.util.Optional;

@RestController
@RequestMapping("/miembro")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveMiembro (@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveMiembro(user));
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> deleteMiembro(@PathVariable Integer id) {
        if (!userService.deleteMiembro(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<User>> getMiembroById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMiembroById(id));
    }

    @PutMapping
    public ResponseEntity<User> editMiembro(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.editMiembro(user));
    }

}

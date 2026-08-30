package scesi.org.check.rol.model.request;

import jakarta.validation.constraints.Size;

public record UpdateRolRequest(
        @Size(max = 50, message = "Rol cannot exceed 50 characters")
        String rol
) {
}

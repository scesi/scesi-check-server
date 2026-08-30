package scesi.org.check.rol.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRolRequest(
        @NotBlank
        @Size(max = 50, message = "rol cannot exceed 50 characters")
        String rol
) {
}

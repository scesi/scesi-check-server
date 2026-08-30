package scesi.org.check.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        String name,

        @Size(max = 50, message = "LastName cannot exceed 50 characters")
        String lastName,

        @Email(message = "Email should be valid")
        String email,
        Boolean active
) {
}

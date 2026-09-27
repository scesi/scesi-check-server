package scesi.org.check.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.auth.service.AuthService;
import scesi.org.check.core.model.response.StandardResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<Void>> login(
            @Validated @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        authService.login(request.email(), response);
        StandardResponse<Void> standardResponse = StandardResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .build();
        return ResponseEntity.ok(standardResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<StandardResponse<Void>> refresh(
            jakarta.servlet.http.HttpServletRequest request,
            HttpServletResponse response
    ) {
        authService.refresh(request, response);
        StandardResponse<Void> standardResponse = StandardResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Token refreshed")
                .build();
        return ResponseEntity.ok(standardResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<StandardResponse<Void>> logout(HttpServletResponse response) {
        authService.logout(response);
        StandardResponse<Void> standardResponse = StandardResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Logout successful")
                .build();
        return ResponseEntity.ok(standardResponse);
    }

    public record LoginRequest(
            @NotBlank(message = "Email is required")
            @Email(message = "Email should be valid")
            String email
    ) {}
}
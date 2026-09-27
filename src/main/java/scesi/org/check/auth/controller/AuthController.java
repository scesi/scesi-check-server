package scesi.org.check.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.auth.model.request.LoginRequest;
import scesi.org.check.auth.model.response.LoginResponse;
import scesi.org.check.auth.service.AuthService;
import scesi.org.check.core.model.response.StandardResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<LoginResponse>> login(
            @Validated @RequestBody LoginRequest request,
            @RequestParam("baseUrl") String baseUrl,
            HttpServletResponse response
    ) {
        AuthService.LoginResult result = authService.login(request.email(), baseUrl, response);
        LoginResponse loginResponse = new LoginResponse(result.emailSent(), result.message());
        StandardResponse<LoginResponse> standardResponse = StandardResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(result.message())
                .data(loginResponse)
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

    @GetMapping("/callback")
    public ResponseEntity<StandardResponse<Void>> callback() {
        StandardResponse<Void> standardResponse = StandardResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .build();
        return ResponseEntity.ok(standardResponse);
    }
}
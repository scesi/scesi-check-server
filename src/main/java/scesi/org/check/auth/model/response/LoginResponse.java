package scesi.org.check.auth.model.response;

public record LoginResponse(
        boolean emailSent,
        String message
) {}
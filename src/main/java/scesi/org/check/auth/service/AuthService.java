package scesi.org.check.auth.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import scesi.org.check.auth.security.JwtUtil;
import scesi.org.check.core.service.EmailService;
import scesi.org.check.core.service.TemplateService;
import scesi.org.check.rol.model.enumerate.RoleEnum;
import scesi.org.check.user.model.repository.IUserRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IUserRepository iUserRepository;
    private final EmailService emailService;
    private final TemplateService templateService;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @Value("${app.cookie.domain}")
    private String cookieDomain;

    @Value("${app.cookie.path}")
    private String cookiePath;

    public LoginResult login(String email, String baseUrl, HttpServletResponse response) {
        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        var roles = user.getRolUser().stream()
                .map(ru -> ru.getRol().getRol())
                .toList();

        if (!roles.contains(RoleEnum.ADMIN.getName())) {
            throw new BadCredentialsException("Only admin users can login");
        }

        String accessToken = jwtUtil.generateAccessToken(email, roles);
        String refreshToken = jwtUtil.generateRefreshToken(email);

        String loginUrl = baseUrl + "/auth/callback?token=" + accessToken;

        boolean emailSent = sendLoginEmail(user.getName(), user.getLastName(), email, loginUrl);

        addAuthCookies(response, accessToken, refreshToken);

        return new LoginResult(emailSent, "If the email exists and has admin role, a login link has been sent");
    }

    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractTokenFromCookie(request, "refresh_token");

        if (refreshToken == null || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        if (!jwtUtil.isTokenValid(refreshToken, email)) {
            throw new BadCredentialsException("Refresh token expired");
        }

        var user = iUserRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        var roles = user.getRolUser().stream()
                .map(ru -> ru.getRol().getRol())
                .toList();

        String newAccessToken = jwtUtil.generateAccessToken(email, roles);
        String newRefreshToken = jwtUtil.generateRefreshToken(email);

        addAuthCookies(response, newAccessToken, newRefreshToken);
    }

    public void logout(HttpServletResponse response) {
        clearAuthCookies(response);
    }

    private boolean sendLoginEmail(String name, String lastName, String email, String loginUrl) {
        try {
            Map<String, Object> data = Map.of(
                    "name", name,
                    "lastName", lastName,
                    "loginUrl", loginUrl
            );
            String html = templateService.getTemplate("email/html/login", data);
            emailService.sendHtmlMessage(email, "Acceso al Sistema - SCESI", html);
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }

    private void addAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessCookie = new Cookie("access_token", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(cookieSecure);
        accessCookie.setDomain(cookieDomain);
        accessCookie.setPath(cookiePath);
        accessCookie.setMaxAge((int) (jwtUtil.getAccessTokenExpiration() / 1000));
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(cookieSecure);
        refreshCookie.setDomain(cookieDomain);
        refreshCookie.setPath(cookiePath);
        refreshCookie.setMaxAge((int) (jwtUtil.getRefreshTokenExpiration() / 1000));
        response.addCookie(refreshCookie);
    }

    private void clearAuthCookies(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("access_token", "");
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(cookieSecure);
        accessCookie.setDomain(cookieDomain);
        accessCookie.setPath(cookiePath);
        accessCookie.setMaxAge(0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(cookieSecure);
        refreshCookie.setDomain(cookieDomain);
        refreshCookie.setPath(cookiePath);
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
    }

    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public record LoginResult(boolean emailSent, String message) {}
}
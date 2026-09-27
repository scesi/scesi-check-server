package scesi.org.check.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import scesi.org.check.auth.security.JwtUtil;
import scesi.org.check.rol.model.enumerate.RoleEnum;
import scesi.org.check.user.model.repository.IUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IUserRepository iUserRepository;

    public void login(String email, HttpServletResponse response) {
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

        addAuthCookies(response, accessToken, refreshToken);
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

    private void addAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessCookie = new Cookie("access_token", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge((int) (jwtUtil.getAccessTokenExpiration() / 1000));
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int) (jwtUtil.getRefreshTokenExpiration() / 1000));
        response.addCookie(refreshCookie);
    }

    private void clearAuthCookies(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("access_token", "");
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
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
}
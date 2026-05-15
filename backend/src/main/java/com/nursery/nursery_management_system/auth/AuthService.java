package com.nursery.nursery_management_system.auth;

import com.nursery.nursery_management_system.auth.dto.LoginRequest;
import com.nursery.nursery_management_system.auth.dto.LoginResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private static final Duration TOKEN_TTL = Duration.ofHours(8);

    private final PasswordEncoder passwordEncoder;
    private final Map<String, DemoUser> usersByEmail = new ConcurrentHashMap<>();
    private final Map<String, SessionToken> sessionsByToken = new ConcurrentHashMap<>();

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void seedDemoUsers() {
        registerDemoUser("admin@nursery.com", "Admin@123", "Admin User", "SUPER_ADMIN", List.of("manage_all", "view_reports"));
        registerDemoUser("manager@nursery.com", "Manager@123", "Operations Manager", "MANAGER", List.of("manage_inventory", "create_order", "view_reports"));
    }

    public LoginResponse login(LoginRequest request) {
        DemoUser user = Optional.ofNullable(usersByEmail.get(request.email().toLowerCase()))
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.passwordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        Instant expiresAt = Instant.now().plus(TOKEN_TTL);
        sessionsByToken.put(token, new SessionToken(user, expiresAt));

        return new LoginResponse(
                token,
                "Bearer",
                expiresAt,
                new LoginResponse.UserInfo(
                        user.id(),
                        user.email(),
                        user.fullName(),
                        user.role(),
                        user.permissions()
                )
        );
    }

    public Optional<AuthPrincipal> authenticateToken(String token) {
        SessionToken session = sessionsByToken.get(token);
        if (session == null) {
            return Optional.empty();
        }

        if (session.expiresAt().isBefore(Instant.now())) {
            sessionsByToken.remove(token);
            return Optional.empty();
        }

        DemoUser user = session.user();
        return Optional.of(new AuthPrincipal(user.id(), user.email(), user.role(), user.permissions()));
    }

    private void registerDemoUser(String email, String rawPassword, String fullName, String role, List<String> permissions) {
        String normalizedEmail = email.toLowerCase();
        usersByEmail.put(normalizedEmail, new DemoUser(
                UUID.nameUUIDFromBytes(normalizedEmail.getBytes()),
                normalizedEmail,
                passwordEncoder.encode(rawPassword),
                fullName,
                role,
                permissions
        ));
    }

    private record DemoUser(
            UUID id,
            String email,
            String passwordHash,
            String fullName,
            String role,
            List<String> permissions
    ) {
    }

    private record SessionToken(
            DemoUser user,
            Instant expiresAt
    ) {
    }

    public record AuthPrincipal(
            UUID id,
            String email,
            String role,
            List<String> permissions
    ) {
    }
}

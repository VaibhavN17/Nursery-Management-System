package com.nursery.nursery_management_system.auth;

import com.nursery.nursery_management_system.auth.dto.LoginRequest;
import com.nursery.nursery_management_system.auth.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @org.springframework.web.bind.annotation.RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}

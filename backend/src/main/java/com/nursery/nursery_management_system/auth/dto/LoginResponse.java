package com.nursery.nursery_management_system.auth.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Instant expiresAt,
        UserInfo user
) {
    public record UserInfo(
            UUID id,
            String email,
            String fullName,
            String role,
            List<String> permissions
    ) {
    }
}

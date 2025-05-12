package com.example.email_validation.auth;

import lombok.Builder;

@Builder
public record AuthenticateResponseDto(
        String token
) {
}

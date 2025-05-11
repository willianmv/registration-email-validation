package com.example.email_validation.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegistrationRequestDto(
        @NotEmpty(message = "Firstname is required")
        @NotBlank(message = "Firstname is required")
        String firstName,

        @NotEmpty(message = "Lastname is required")
        @NotBlank(message = "Lastname is required")
        String lasName,

        @NotEmpty(message = "Email is required")
        @NotBlank(message = "Email is required")
        @Email(message = "Email is not formatted") String email,

        @NotEmpty(message = "Password is required")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must have 8 characters long minimum")
        String password
)
{
}

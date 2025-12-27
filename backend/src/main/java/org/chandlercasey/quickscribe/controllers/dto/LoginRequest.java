package org.chandlercasey.quickscribe.controllers.dto;
import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank String password
) {}


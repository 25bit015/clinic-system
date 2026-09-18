package com.clinic.system.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String fullName,
        Set<String> roles
) {
}

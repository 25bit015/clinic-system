package com.clinic.system.dto;

import com.clinic.system.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientRequest(
        @NotBlank String fullName,
        @NotNull @Min(0) Integer age,
        @NotNull Gender gender,
        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank String emergencyContact
) {
}

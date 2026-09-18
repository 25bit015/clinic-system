package com.clinic.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrescriptionRequest(
        @NotNull Long consultationId,
        @NotNull Long patientId,
        @NotNull Long drugId,
        @NotBlank String dosage,
        @NotBlank String frequency,
        @NotBlank String duration,
        @NotNull Integer quantity,
        @NotBlank String instructions
) {
}

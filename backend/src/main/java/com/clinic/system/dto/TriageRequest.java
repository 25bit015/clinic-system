package com.clinic.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TriageRequest(
        @NotNull Long appointmentId,
        @NotNull Long patientId,
        @NotBlank String bloodPressure,
        @NotNull Double temperature,
        @NotNull Double weight,
        @NotNull Double height,
        @NotNull Integer pulse,
        @NotNull Integer spo2,
        @NotBlank String chiefComplaint
) {
}

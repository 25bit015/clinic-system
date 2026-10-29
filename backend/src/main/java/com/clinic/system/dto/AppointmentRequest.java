package com.clinic.system.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(
        @NotNull Long patientId,
        Long doctorId,
        @NotNull @FutureOrPresent LocalDateTime appointmentTime,
        @NotBlank String department,
        @NotBlank String reason
) {
}

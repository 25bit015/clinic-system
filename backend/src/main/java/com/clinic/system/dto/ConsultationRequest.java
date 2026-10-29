package com.clinic.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultationRequest(
        @NotNull Long appointmentId,
        @NotNull Long patientId,
        @NotNull Long doctorId,
        @NotBlank String clinicalNotes,
        LocalDateTime followUpDate,
        String referralSpecialist,
        boolean admitted
) {
}

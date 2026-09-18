package com.clinic.system.dto;

import jakarta.validation.constraints.NotNull;

public record LabOrderRequest(
        @NotNull Long consultationId,
        @NotNull Long patientId,
        @NotNull Long labTestId
) {
}

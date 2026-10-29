package com.clinic.system.dto;

import com.clinic.system.enums.LabOrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LabResultRequest(
        @NotNull Long labOrderId,
        @NotBlank String resultNotes,
        String reportUrl,
        @NotNull LabOrderStatus status
) {
}

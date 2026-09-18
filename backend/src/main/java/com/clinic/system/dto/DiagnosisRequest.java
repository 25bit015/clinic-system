package com.clinic.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiagnosisRequest(
        @NotNull Long consultationId,
        @NotBlank String icd10Code,
        @NotBlank String description,
        boolean primaryDiagnosis
) {
}

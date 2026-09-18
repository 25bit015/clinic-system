package com.clinic.system.dto;

import com.clinic.system.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull Long invoiceId,
        @NotNull BigDecimal amount,
        @NotNull PaymentMethod paymentMethod,
        String insuranceProvider
) {
}

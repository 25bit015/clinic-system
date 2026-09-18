package com.clinic.system.controller;

import com.clinic.system.dto.PaymentRequest;
import com.clinic.system.entity.Invoice;
import com.clinic.system.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/invoices/generate/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice generateInvoice(@PathVariable Long appointmentId) {
        return billingService.generateInvoice(appointmentId);
    }

    @PostMapping("/payments")
    public Invoice recordPayment(@Valid @RequestBody PaymentRequest request) {
        return billingService.recordPayment(request);
    }
}

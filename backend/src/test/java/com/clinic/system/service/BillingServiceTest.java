package com.clinic.system.service;

import com.clinic.system.dto.PaymentRequest;
import com.clinic.system.entity.Invoice;
import com.clinic.system.enums.PaymentMethod;
import com.clinic.system.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private LabOrderRepository labOrderRepository;
    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private BillingService billingService;

    @Test
    void recordPaymentRejectsNonPositiveAmount() {
        Invoice invoice = new Invoice();
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setTotalAmount(BigDecimal.TEN);

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        PaymentRequest request = new PaymentRequest(1L, BigDecimal.ZERO, PaymentMethod.CASH, null);
        assertThatThrownBy(() -> billingService.recordPayment(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Payment amount must be greater than zero");
    }
}

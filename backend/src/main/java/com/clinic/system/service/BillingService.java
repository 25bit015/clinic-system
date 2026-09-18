package com.clinic.system.service;

import com.clinic.system.dto.PaymentRequest;
import com.clinic.system.entity.*;
import com.clinic.system.enums.InvoiceItemType;
import com.clinic.system.enums.PaymentStatus;
import com.clinic.system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BillingService {

    private static final BigDecimal DEFAULT_CONSULTATION_FEE = BigDecimal.valueOf(2000);

    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final LabOrderRepository labOrderRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Transactional
    public Invoice generateInvoice(Long appointmentId) {
        return invoiceRepository.findByAppointmentId(appointmentId).orElseGet(() -> createInvoice(appointmentId));
    }

    private Invoice createInvoice(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Appointment not found"));

        Invoice invoice = new Invoice();
        invoice.setAppointment(appointment);
        invoice.setPatient(appointment.getPatient());

        List<InvoiceItem> items = new ArrayList<>();

        items.add(item(invoice, InvoiceItemType.CONSULTATION, "Consultation Fee", DEFAULT_CONSULTATION_FEE, appointmentId));

        labOrderRepository.findAll().stream()
                .filter(order -> order.getConsultation().getAppointment().getId().equals(appointmentId))
                .forEach(order -> items.add(item(
                        invoice,
                        InvoiceItemType.LAB,
                        "Lab: " + order.getLabTest().getName(),
                        order.getLabTest().getPrice(),
                        order.getId()
                )));

        prescriptionRepository.findAll().stream()
                .filter(prescription -> prescription.isDispensed()
                        && prescription.getConsultation().getAppointment().getId().equals(appointmentId))
                .forEach(prescription -> {
                    BigDecimal amount = prescription.getDrug().getUnitPrice().multiply(BigDecimal.valueOf(prescription.getQuantity()));
                    items.add(item(
                            invoice,
                            InvoiceItemType.PHARMACY,
                            "Drug: " + prescription.getDrug().getDrugName(),
                            amount,
                            prescription.getId()
                    ));
                });

        invoice.setItems(items);
        invoice.setTotalAmount(items.stream().map(InvoiceItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice recordPayment(PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.invoiceId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Invoice not found"));
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(BAD_REQUEST, "Payment amount must be greater than zero");
        }

        BigDecimal newPaidAmount = invoice.getPaidAmount().add(request.amount());
        invoice.setPaidAmount(newPaidAmount);
        invoice.setPaymentMethod(request.paymentMethod());
        invoice.setInsuranceProvider(request.insuranceProvider());

        if (newPaidAmount.compareTo(invoice.getTotalAmount()) >= 0) {
            invoice.setPaymentStatus(PaymentStatus.PAID);
            invoice.getAppointment().setStatus(com.clinic.system.enums.AppointmentStatus.COMPLETED);
        } else {
            invoice.setPaymentStatus(PaymentStatus.PARTIAL);
        }

        return invoiceRepository.save(invoice);
    }

    private InvoiceItem item(Invoice invoice, InvoiceItemType type, String description, BigDecimal amount, Long referenceId) {
        InvoiceItem item = new InvoiceItem();
        item.setInvoice(invoice);
        item.setItemType(type);
        item.setDescription(description);
        item.setAmount(amount);
        item.setReferenceId(referenceId);
        return item;
    }
}

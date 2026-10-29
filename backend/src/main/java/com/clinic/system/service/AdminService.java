package com.clinic.system.service;

import com.clinic.system.entity.DrugInventory;
import com.clinic.system.entity.User;
import com.clinic.system.repository.AppointmentRepository;
import com.clinic.system.repository.DrugInventoryRepository;
import com.clinic.system.repository.InvoiceRepository;
import com.clinic.system.repository.PatientRepository;
import com.clinic.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final DrugInventoryRepository drugInventoryRepository;

    public List<User> users() {
        return userRepository.findAll();
    }

    public List<DrugInventory> drugs() {
        return drugInventoryRepository.findAll();
    }

    public Map<String, Object> dailyReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("totalPatients", patientRepository.count());
        report.put("totalAppointments", appointmentRepository.count());
        report.put("revenue", invoiceRepository.findAll().stream()
                .map(invoice -> invoice.getPaidAmount() == null ? BigDecimal.ZERO : invoice.getPaidAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        report.put("lowStockItems", drugInventoryRepository.findAll().stream()
                .filter(drug -> drug.getStockQuantity() <= drug.getReorderLevel())
                .count());
        return report;
    }
}

package com.clinic.system.service;

import com.clinic.system.entity.DrugInventory;
import com.clinic.system.entity.Prescription;
import com.clinic.system.enums.AppointmentStatus;
import com.clinic.system.repository.DrugInventoryRepository;
import com.clinic.system.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PrescriptionRepository prescriptionRepository;
    private final DrugInventoryRepository drugInventoryRepository;

    public List<Prescription> pendingPrescriptions() {
        return prescriptionRepository.findByDispensedFalse();
    }

    public List<DrugInventory> lowStockItems() {
        return drugInventoryRepository.findAll().stream()
                .filter(drug -> drug.getStockQuantity() <= drug.getReorderLevel())
                .toList();
    }

    @Transactional
    public Prescription dispense(Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Prescription not found"));
        DrugInventory drug = prescription.getDrug();

        if (drug.getStockQuantity() < prescription.getQuantity()) {
            throw new ResponseStatusException(BAD_REQUEST, "Insufficient stock");
        }

        drug.setStockQuantity(drug.getStockQuantity() - prescription.getQuantity());
        drugInventoryRepository.save(drug);

        prescription.setDispensed(true);
        prescription.getConsultation().getAppointment().setStatus(AppointmentStatus.BILLING);
        return prescriptionRepository.save(prescription);
    }
}

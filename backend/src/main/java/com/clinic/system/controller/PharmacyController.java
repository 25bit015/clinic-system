package com.clinic.system.controller;

import com.clinic.system.entity.DrugInventory;
import com.clinic.system.entity.Prescription;
import com.clinic.system.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping("/prescriptions/pending")
    public List<Prescription> pendingPrescriptions() {
        return pharmacyService.pendingPrescriptions();
    }

    @GetMapping("/inventory/low-stock")
    public List<DrugInventory> lowStock() {
        return pharmacyService.lowStockItems();
    }

    @PostMapping("/prescriptions/{id}/dispense")
    public Prescription dispense(@PathVariable Long id) {
        return pharmacyService.dispense(id);
    }
}

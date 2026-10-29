package com.clinic.system.controller;

import com.clinic.system.dto.ConsultationRequest;
import com.clinic.system.dto.DiagnosisRequest;
import com.clinic.system.dto.LabOrderRequest;
import com.clinic.system.dto.PrescriptionRequest;
import com.clinic.system.entity.*;
import com.clinic.system.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/queue")
    public List<Appointment> queue() {
        return doctorService.doctorQueue();
    }

    @GetMapping("/patients/{patientId}/history")
    public List<Consultation> patientHistory(@PathVariable Long patientId) {
        return doctorService.patientHistory(patientId);
    }

    @PostMapping("/consultations")
    @ResponseStatus(HttpStatus.CREATED)
    public Consultation createConsultation(@Valid @RequestBody ConsultationRequest request) {
        return doctorService.createConsultation(request);
    }

    @PostMapping("/diagnosis")
    @ResponseStatus(HttpStatus.CREATED)
    public Diagnosis addDiagnosis(@Valid @RequestBody DiagnosisRequest request) {
        return doctorService.addDiagnosis(request);
    }

    @PostMapping("/lab-orders")
    @ResponseStatus(HttpStatus.CREATED)
    public LabOrder orderLab(@Valid @RequestBody LabOrderRequest request) {
        return doctorService.orderLabTest(request);
    }

    @PostMapping("/prescriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public Prescription prescribe(@Valid @RequestBody PrescriptionRequest request) {
        return doctorService.prescribe(request);
    }
}

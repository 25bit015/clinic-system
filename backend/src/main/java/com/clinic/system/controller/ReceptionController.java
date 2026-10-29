package com.clinic.system.controller;

import com.clinic.system.dto.AppointmentRequest;
import com.clinic.system.dto.PatientRequest;
import com.clinic.system.entity.Appointment;
import com.clinic.system.entity.Patient;
import com.clinic.system.service.ReceptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reception")
@RequiredArgsConstructor
public class ReceptionController {

    private final ReceptionService receptionService;

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public Patient registerPatient(@Valid @RequestBody PatientRequest request) {
        return receptionService.registerPatient(request);
    }

    @GetMapping("/patients/search")
    public List<Patient> searchPatients(@RequestParam String q) {
        return receptionService.searchPatients(q);
    }

    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment bookAppointment(@Valid @RequestBody AppointmentRequest request) {
        return receptionService.bookAppointment(request);
    }

    @PatchMapping("/appointments/{id}/triage-queue")
    public Appointment assignToTriage(@PathVariable Long id) {
        return receptionService.assignToTriageQueue(id);
    }

    @GetMapping("/triage-queue")
    public List<Appointment> triageQueue() {
        return receptionService.triageQueue();
    }

    @GetMapping("/patients/{id}/timeline")
    public Map<String, Object> patientTimeline(@PathVariable Long id) {
        return receptionService.patientTimeline(id);
    }
}

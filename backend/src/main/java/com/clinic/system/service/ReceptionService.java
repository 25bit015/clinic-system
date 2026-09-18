package com.clinic.system.service;

import com.clinic.system.dto.AppointmentRequest;
import com.clinic.system.dto.PatientRequest;
import com.clinic.system.entity.Appointment;
import com.clinic.system.entity.Patient;
import com.clinic.system.entity.User;
import com.clinic.system.enums.AppointmentStatus;
import com.clinic.system.repository.AppointmentRepository;
import com.clinic.system.repository.ConsultationRepository;
import com.clinic.system.repository.InvoiceRepository;
import com.clinic.system.repository.PatientRepository;
import com.clinic.system.repository.TriageVitalRepository;
import com.clinic.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final TriageVitalRepository triageVitalRepository;
    private final ConsultationRepository consultationRepository;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public Patient registerPatient(PatientRequest request) {
        Patient patient = new Patient();
        patient.setFileNumber(generateFileNumber());
        patient.setFullName(request.fullName());
        patient.setAge(request.age());
        patient.setGender(request.gender());
        patient.setPhone(request.phone());
        patient.setAddress(request.address());
        patient.setEmergencyContact(request.emergencyContact());
        return patientRepository.save(patient);
    }

    @Transactional
    public Appointment bookAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Patient not found"));

        User doctor = null;
        if (request.doctorId() != null) {
            doctor = userRepository.findById(request.doctorId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor not found"));
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.appointmentTime());
        appointment.setDepartment(request.department());
        appointment.setReason(request.reason());
        appointment.setStatus(AppointmentStatus.TRIAGE);
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment assignToTriageQueue(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Appointment not found"));
        appointment.setStatus(AppointmentStatus.TRIAGE);
        return appointmentRepository.save(appointment);
    }

    public List<Patient> searchPatients(String query) {
        return patientRepository.findByFullNameContainingIgnoreCaseOrFileNumberContainingIgnoreCase(query, query);
    }

    public List<Appointment> triageQueue() {
        return appointmentRepository.findByStatus(AppointmentStatus.TRIAGE);
    }

    public Map<String, Object> patientTimeline(Long patientId) {
        return Map.of(
                "appointments", appointmentRepository.findByPatientIdOrderByAppointmentTimeDesc(patientId),
                "triageVitals", triageVitalRepository.findByPatientIdOrderByCreatedAtDesc(patientId),
                "consultations", consultationRepository.findByPatientIdOrderByConsultationDateDesc(patientId),
                "invoices", invoiceRepository.findAll().stream()
                        .filter(invoice -> invoice.getPatient().getId().equals(patientId))
                        .toList()
        );
    }

    private String generateFileNumber() {
        String dateSegment = LocalDate.now().toString().replace("-", "");
        int suffix = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "CLN-" + dateSegment + "-" + suffix;
    }
}

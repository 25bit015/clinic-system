package com.clinic.system.service;

import com.clinic.system.dto.ConsultationRequest;
import com.clinic.system.dto.DiagnosisRequest;
import com.clinic.system.dto.LabOrderRequest;
import com.clinic.system.dto.PrescriptionRequest;
import com.clinic.system.entity.*;
import com.clinic.system.enums.AppointmentStatus;
import com.clinic.system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ConsultationRepository consultationRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final LabOrderRepository labOrderRepository;
    private final LabTestCatalogRepository labTestCatalogRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DrugInventoryRepository drugInventoryRepository;

    public List<Appointment> doctorQueue() {
        return appointmentRepository.findByStatus(AppointmentStatus.DOCTOR_QUEUE);
    }

    @Transactional
    public Consultation createConsultation(ConsultationRequest request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Appointment not found"));
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Patient not found"));
        User doctor = userRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor not found"));

        Consultation consultation = new Consultation();
        consultation.setAppointment(appointment);
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setClinicalNotes(request.clinicalNotes());
        consultation.setConsultationDate(LocalDateTime.now());
        consultation.setFollowUpDate(request.followUpDate());
        consultation.setReferralSpecialist(request.referralSpecialist());
        consultation.setAdmitted(request.admitted());

        appointment.setStatus(AppointmentStatus.CONSULTING);
        appointmentRepository.save(appointment);

        return consultationRepository.save(consultation);
    }

    @Transactional
    public Diagnosis addDiagnosis(DiagnosisRequest request) {
        Consultation consultation = consultationRepository.findById(request.consultationId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Consultation not found"));

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setConsultation(consultation);
        diagnosis.setIcd10Code(request.icd10Code());
        diagnosis.setDescription(request.description());
        diagnosis.setPrimaryDiagnosis(request.primaryDiagnosis());

        return diagnosisRepository.save(diagnosis);
    }

    @Transactional
    public LabOrder orderLabTest(LabOrderRequest request) {
        Consultation consultation = consultationRepository.findById(request.consultationId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Consultation not found"));
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Patient not found"));
        LabTestCatalog testCatalog = labTestCatalogRepository.findById(request.labTestId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Lab test not found"));

        LabOrder order = new LabOrder();
        order.setConsultation(consultation);
        order.setPatient(patient);
        order.setLabTest(testCatalog);

        consultation.getAppointment().setStatus(AppointmentStatus.LAB);
        appointmentRepository.save(consultation.getAppointment());

        return labOrderRepository.save(order);
    }

    @Transactional
    public Prescription prescribe(PrescriptionRequest request) {
        Consultation consultation = consultationRepository.findById(request.consultationId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Consultation not found"));
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Patient not found"));
        DrugInventory drug = drugInventoryRepository.findById(request.drugId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Drug not found"));

        Prescription prescription = new Prescription();
        prescription.setConsultation(consultation);
        prescription.setPatient(patient);
        prescription.setDrug(drug);
        prescription.setDosage(request.dosage());
        prescription.setFrequency(request.frequency());
        prescription.setDuration(request.duration());
        prescription.setQuantity(request.quantity());
        prescription.setInstructions(request.instructions());
        prescription.setDispensed(false);

        consultation.getAppointment().setStatus(AppointmentStatus.PHARMACY);
        appointmentRepository.save(consultation.getAppointment());

        return prescriptionRepository.save(prescription);
    }

    public List<Consultation> patientHistory(Long patientId) {
        return consultationRepository.findByPatientIdOrderByConsultationDateDesc(patientId);
    }
}

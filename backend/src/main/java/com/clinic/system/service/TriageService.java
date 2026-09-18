package com.clinic.system.service;

import com.clinic.system.dto.TriageRequest;
import com.clinic.system.entity.Appointment;
import com.clinic.system.entity.Patient;
import com.clinic.system.entity.TriageVital;
import com.clinic.system.enums.AppointmentStatus;
import com.clinic.system.repository.AppointmentRepository;
import com.clinic.system.repository.PatientRepository;
import com.clinic.system.repository.TriageVitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TriageService {

    private final TriageVitalRepository triageVitalRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public TriageVital recordVitals(TriageRequest request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Appointment not found"));
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Patient not found"));

        TriageVital vital = new TriageVital();
        vital.setAppointment(appointment);
        vital.setPatient(patient);
        vital.setBloodPressure(request.bloodPressure());
        vital.setTemperature(request.temperature());
        vital.setWeight(request.weight());
        vital.setHeight(request.height());
        vital.setPulse(request.pulse());
        vital.setSpo2(request.spo2());
        vital.setChiefComplaint(request.chiefComplaint());

        appointment.setStatus(AppointmentStatus.DOCTOR_QUEUE);
        appointmentRepository.save(appointment);

        return triageVitalRepository.save(vital);
    }
}

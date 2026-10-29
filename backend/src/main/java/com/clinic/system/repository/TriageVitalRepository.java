package com.clinic.system.repository;

import com.clinic.system.entity.TriageVital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TriageVitalRepository extends JpaRepository<TriageVital, Long> {
    Optional<TriageVital> findByAppointmentId(Long appointmentId);
    List<TriageVital> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}

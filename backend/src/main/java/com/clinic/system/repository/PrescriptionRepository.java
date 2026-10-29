package com.clinic.system.repository;

import com.clinic.system.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByDispensedFalse();
    List<Prescription> findByConsultationId(Long consultationId);
}

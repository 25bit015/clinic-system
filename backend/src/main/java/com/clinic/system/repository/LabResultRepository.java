package com.clinic.system.repository;

import com.clinic.system.entity.LabResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {
    Optional<LabResult> findByLabOrderId(Long labOrderId);
}

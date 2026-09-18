package com.clinic.system.repository;

import com.clinic.system.entity.LabOrder;
import com.clinic.system.enums.LabOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {
    List<LabOrder> findByStatus(LabOrderStatus status);
    List<LabOrder> findByConsultationId(Long consultationId);
}

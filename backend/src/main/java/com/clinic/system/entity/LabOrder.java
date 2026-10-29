package com.clinic.system.entity;

import com.clinic.system.enums.LabOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lab_orders")
public class LabOrder extends BaseEntity {

    @ManyToOne(optional = false)
    private Consultation consultation;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private LabTestCatalog labTest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabOrderStatus status = LabOrderStatus.PENDING;
}

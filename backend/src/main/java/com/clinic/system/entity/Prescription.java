package com.clinic.system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {

    @ManyToOne(optional = false)
    private Consultation consultation;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private DrugInventory drug;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, length = 1000)
    private String instructions;

    @Column(nullable = false)
    private boolean dispensed;
}

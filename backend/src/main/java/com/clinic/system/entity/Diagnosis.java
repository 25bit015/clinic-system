package com.clinic.system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "diagnosis")
public class Diagnosis extends BaseEntity {

    @ManyToOne(optional = false)
    private Consultation consultation;

    @Column(nullable = false)
    private String icd10Code;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean primaryDiagnosis;
}

package com.clinic.system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "consultations")
public class Consultation extends BaseEntity {

    @OneToOne(optional = false)
    private Appointment appointment;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private User doctor;

    @Column(nullable = false, length = 3000)
    private String clinicalNotes;

    @Column(nullable = false)
    private LocalDateTime consultationDate;

    private LocalDateTime followUpDate;

    private String referralSpecialist;

    @Column(nullable = false)
    private boolean admitted;
}

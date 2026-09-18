package com.clinic.system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "triage_vitals")
public class TriageVital extends BaseEntity {

    @ManyToOne(optional = false)
    private Patient patient;

    @OneToOne(optional = false)
    private Appointment appointment;

    @Column(nullable = false)
    private String bloodPressure;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Integer pulse;

    @Column(nullable = false)
    private Integer spo2;

    @Column(nullable = false, length = 1500)
    private String chiefComplaint;
}

package com.clinic.system.entity;

import com.clinic.system.enums.LabOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lab_results")
public class LabResult extends BaseEntity {

    @OneToOne(optional = false)
    private LabOrder labOrder;

    @Column(nullable = false, length = 2000)
    private String resultNotes;

    private String reportUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabOrderStatus status = LabOrderStatus.COMPLETED;
}

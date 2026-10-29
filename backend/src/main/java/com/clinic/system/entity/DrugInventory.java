package com.clinic.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "drugs_inventory")
public class DrugInventory extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String drugName;

    @Column(nullable = false)
    private String batchNumber;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer reorderLevel;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;
}

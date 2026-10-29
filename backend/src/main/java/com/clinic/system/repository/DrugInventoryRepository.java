package com.clinic.system.repository;

import com.clinic.system.entity.DrugInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrugInventoryRepository extends JpaRepository<DrugInventory, Long> {
    List<DrugInventory> findByStockQuantityLessThanEqual(Integer stockLevel);
}

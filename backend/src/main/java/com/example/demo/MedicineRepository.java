package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {
    List<Medicine> findByPatientId(String patientId);
    List<Medicine> findByStockLessThan(int stockThreshold);
}

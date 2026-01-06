package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "http://localhost:5173")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    // GET all medicines
    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        System.out.println("💊 GET /api/medicines");
        try {
            List<Medicine> medicines = medicineRepository.findAll();
            System.out.println("✅ Found " + medicines.size() + " medicines");
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            System.err.println("❌ Error fetching medicines: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET single medicine
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable String id) {
        System.out.println("🔍 GET /api/medicines/" + id);
        return medicineRepository.findById(id)
                .map(medicine -> {
                    System.out.println("✅ Found: " + medicine);
                    return ResponseEntity.ok(medicine);
                })
                .orElseGet(() -> {
                    System.out.println("❌ Not found: " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    // GET medicines by patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Medicine>> getMedicinesByPatient(@PathVariable String patientId) {
        System.out.println("🔍 GET /api/medicines/patient/" + patientId);
        try {
            List<Medicine> medicines = medicineRepository.findByPatientId(patientId);
            System.out.println("✅ Found " + medicines.size() + " medicines for patient " + patientId);
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET low stock medicines
    @GetMapping("/low-stock/{threshold}")
    public ResponseEntity<List<Medicine>> getLowStockMedicines(@PathVariable int threshold) {
        System.out.println("⚠️ GET /api/medicines/low-stock/" + threshold);
        try {
            List<Medicine> medicines = medicineRepository.findByStockLessThan(threshold);
            System.out.println("✅ Found " + medicines.size() + " low stock medicines");
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Create new medicine
    @PostMapping
    public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine medicine) {
        System.out.println("===========================================");
        System.out.println("💊 POST /api/medicines - NEW REQUEST");
        System.out.println("Received Medicine: " + medicine);
        System.out.println("ID: " + medicine.getId());
        System.out.println("Patient ID: " + medicine.getPatientId());
        System.out.println("Name: " + medicine.getName());
        System.out.println("Dosage: " + medicine.getDosage());
        System.out.println("Schedule: " + medicine.getSchedule());
        System.out.println("Stock: " + medicine.getStock());

        // Validation
        if (medicine.getId() == null || medicine.getId().trim().isEmpty()) {
            System.err.println("❌ ERROR: ID is null or empty!");
            return ResponseEntity.badRequest().build();
        }

        if (medicine.getPatientId() == null || medicine.getPatientId().trim().isEmpty()) {
            System.err.println("❌ ERROR: Patient ID is null or empty!");
            return ResponseEntity.badRequest().build();
        }

        if (medicine.getName() == null || medicine.getName().trim().isEmpty()) {
            System.err.println("❌ ERROR: Medicine name is null or empty!");
            return ResponseEntity.badRequest().build();
        }

        try {
            // Save to database
            Medicine savedMedicine = medicineRepository.save(medicine);
            System.out.println("✅ SAVED TO DATABASE: " + savedMedicine);

            // Verify it was saved
            boolean exists = medicineRepository.existsById(savedMedicine.getId());
            System.out.println("🔍 Verification - Exists in DB: " + exists);

            if (exists) {
                Medicine verified = medicineRepository.findById(savedMedicine.getId()).orElse(null);
                System.out.println("✅ VERIFIED: " + verified);
            }

            System.out.println("===========================================");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicine);

        } catch (Exception e) {
            System.err.println("❌ EXCEPTION OCCURRED:");
            System.err.println("Type: " + e.getClass().getName());
            System.err.println("Message: " + e.getMessage());
            System.err.println("Cause: " + e.getCause());
            e.printStackTrace();
            System.out.println("===========================================");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT - Update medicine
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable String id, @RequestBody Medicine medicineDetails) {
        System.out.println("✏️ PUT /api/medicines/" + id);
        return medicineRepository.findById(id)
                .map(medicine -> {
                    medicine.setPatientId(medicineDetails.getPatientId());
                    medicine.setName(medicineDetails.getName());
                    medicine.setDosage(medicineDetails.getDosage());
                    medicine.setSchedule(medicineDetails.getSchedule());
                    medicine.setStock(medicineDetails.getStock());
                    Medicine updated = medicineRepository.save(medicine);
                    System.out.println("✅ Updated: " + updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    System.out.println("❌ Medicine not found: " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    // PATCH - Update stock only
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Medicine> updateMedicineStock(@PathVariable String id, @RequestBody int newStock) {
        System.out.println("📦 PATCH /api/medicines/" + id + "/stock → " + newStock);
        return medicineRepository.findById(id)
                .map(medicine -> {
                    medicine.setStock(newStock);
                    Medicine updated = medicineRepository.save(medicine);
                    System.out.println("✅ Updated stock: " + updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    System.out.println("❌ Medicine not found: " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    // DELETE medicine
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable String id) {
        System.out.println("🗑️ DELETE /api/medicines/" + id);
        try {
            if (medicineRepository.existsById(id)) {
                medicineRepository.deleteById(id);
                System.out.println("✅ Deleted medicine: " + id);
                return ResponseEntity.ok().build();
            }
            System.out.println("❌ Medicine not found: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("❌ Error deleting medicine: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET count
    @GetMapping("/count")
    public ResponseEntity<Long> getMedicineCount() {
        try {
            long count = medicineRepository.count();
            System.out.println("📊 Medicine count: " + count);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            System.err.println("❌ Error counting medicines: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
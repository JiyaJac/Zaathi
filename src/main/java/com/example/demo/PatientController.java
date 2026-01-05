package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<Patient> getAllPatients() {
        System.out.println("📋 GET /api/patients - Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        System.out.println("✅ Found " + patients.size() + " patients");
        return patients;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        System.out.println("🔍 GET /api/patients/" + id);
        return patientRepository.findById(id)
                .map(patient -> {
                    System.out.println("✅ Found patient: " + patient);
                    return ResponseEntity.ok(patient);
                })
                .orElseGet(() -> {
                    System.out.println("❌ Patient not found with id: " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        System.out.println("===============================================");
        System.out.println("📝 POST /api/patients - RECEIVED REQUEST");
        System.out.println("Patient Data: " + patient);
        System.out.println("ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Age: " + patient.getAge());
        System.out.println("Condition: " + patient.getCondition());

        try {
            Patient savedPatient = patientRepository.save(patient);
            System.out.println("✅ SUCCESSFULLY SAVED TO DATABASE");
            System.out.println("Saved Patient: " + savedPatient);
            System.out.println("===============================================");
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
        } catch (Exception e) {
            System.err.println("❌ ERROR SAVING PATIENT:");
            System.err.println("Error Type: " + e.getClass().getName());
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
            System.out.println("===============================================");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient patientDetails) {
        System.out.println("✏️ PUT /api/patients/" + id);
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setName(patientDetails.getName());
                    patient.setAge(patientDetails.getAge());
                    patient.setCondition(patientDetails.getCondition());
                    Patient updated = patientRepository.save(patient);
                    System.out.println("✅ Updated patient: " + updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    System.out.println("❌ Patient not found for update: " + id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        System.out.println("🗑️ DELETE /api/patients/" + id);
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            System.out.println("✅ Deleted patient with id: " + id);
            return ResponseEntity.ok().build();
        }
        System.out.println("❌ Patient not found for deletion: " + id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public long getPatientCount() {
        long count = patientRepository.count();
        System.out.println("📊 Total patients: " + count);
        return count;
    }
}
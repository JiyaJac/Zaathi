package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctor_notes")
public class DoctorNote {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String note;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime timestamp;

    // Automatically runs before INSERT
    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
    }

    // Required by JPA
    public DoctorNote() {}

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DoctorNote{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", note='" + note + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

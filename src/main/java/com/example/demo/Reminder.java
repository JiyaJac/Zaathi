package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    private String id;

    @Column(nullable = false)
    private String patientId;

    @Column(nullable = false, length = 500)
    private String task;

    @Column(nullable = false)
    private String time;  // Format: "HH:mm" (e.g., "09:30")

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Reminder() {
        this.createdAt = LocalDateTime.now();
    }

    public Reminder(String id, String patientId, String task, String time, boolean completed) {
        this.id = id;
        this.patientId = patientId;
        this.task = task;
        this.time = time;
        this.completed = completed;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Reminder{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", task='" + task + '\'' +
                ", time='" + time + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    private String id;

    @Column(nullable = false)
    private String patientId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String schedule;

    @Column(nullable = false)
    private int stock;

    // Default constructor (required by JPA)
    public Medicine() {}

    // Constructor with all fields
    public Medicine(String id, String patientId, String name, String dosage, String schedule, int stock) {
        this.id = id;
        this.patientId = patientId;
        this.name = name;
        this.dosage = dosage;
        this.schedule = schedule;
        this.stock = stock;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", name='" + name + '\'' +
                ", dosage='" + dosage + '\'' +
                ", schedule='" + schedule + '\'' +
                ", stock=" + stock +
                '}';
    }
}
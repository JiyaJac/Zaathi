package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    private String id;

    private String name;
    private int age;

    // This is the fix! It tells MySQL to name the column "health_condition"
    // instead of the forbidden word "condition"
    @Column(name = "health_condition", length = 1000, nullable = false)
    private String condition;

    public Patient() {}

    // GETTERS AND SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
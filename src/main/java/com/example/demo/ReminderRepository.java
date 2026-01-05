package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, String> {
    List<Reminder> findByPatientId(String patientId);
    List<Reminder> findByCompleted(boolean completed);
    List<Reminder> findByPatientIdAndCompleted(String patientId, boolean completed);
}
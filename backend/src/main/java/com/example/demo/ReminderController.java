package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "http://localhost:5173")
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping
    public ResponseEntity<List<Reminder>> getAllReminders() {
        System.out.println("⏰ GET /api/reminders");
        List<Reminder> reminders = reminderRepository.findAll();
        System.out.println("✅ Found " + reminders.size() + " reminders");
        return ResponseEntity.ok(reminders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable String id) {
        return reminderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Reminder>> getRemindersByPatient(@PathVariable String patientId) {
        List<Reminder> reminders = reminderRepository.findByPatientId(patientId);
        return ResponseEntity.ok(reminders);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Reminder>> getPendingReminders() {
        List<Reminder> reminders = reminderRepository.findByCompleted(false);
        return ResponseEntity.ok(reminders);
    }

    @PostMapping
    public ResponseEntity<Reminder> addReminder(@RequestBody Reminder reminder) {
        System.out.println("⏰ POST /api/reminders - NEW REQUEST");
        System.out.println("Received: " + reminder);

        try {
            Reminder saved = reminderRepository.save(reminder);
            System.out.println("✅ SAVED: " + saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable String id, @RequestBody Reminder reminderDetails) {
        return reminderRepository.findById(id)
                .map(reminder -> {
                    reminder.setTask(reminderDetails.getTask());
                    reminder.setTime(reminderDetails.getTime());
                    reminder.setCompleted(reminderDetails.isCompleted());
                    return ResponseEntity.ok(reminderRepository.save(reminder));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Reminder> markReminderComplete(@PathVariable String id) {
        return reminderRepository.findById(id)
                .map(reminder -> {
                    reminder.setCompleted(true);
                    return ResponseEntity.ok(reminderRepository.save(reminder));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable String id) {
        if (reminderRepository.existsById(id)) {
            reminderRepository.deleteById(id);
            System.out.println("✅ Deleted reminder: " + id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
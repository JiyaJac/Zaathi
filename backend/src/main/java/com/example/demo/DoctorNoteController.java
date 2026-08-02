package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctornotes")
@CrossOrigin(origins = "http://localhost:3000") // Fixes potential CORS errors
public class DoctorNoteController {

    @Autowired
    private DoctorNoteRepository doctorNoteRepository;

    // GET all notes
    @GetMapping
    public List<DoctorNote> getAllNotes() {
        System.out.println("📋 GET /api/doctornotes - Fetching all notes");
        return doctorNoteRepository.findAll();
    }

    // POST a new note
    @PostMapping
    public ResponseEntity<DoctorNote> addNote(@RequestBody DoctorNote note) {
        System.out.println("📋 POST /api/doctornotes - Received: " + note.getNote());

        try {
            // The @PrePersist in your Entity will handle the ID and Timestamp automatically
            DoctorNote savedNote = doctorNoteRepository.save(note);
            System.out.println("✅ Note saved successfully with ID: " + savedNote.getId());
            return ResponseEntity.ok(savedNote);
        } catch (Exception e) {
            System.err.println("❌ ERROR SAVING NOTE: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
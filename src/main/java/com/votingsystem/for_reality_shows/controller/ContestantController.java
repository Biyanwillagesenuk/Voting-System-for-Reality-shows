package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.model.Contestant;
import com.votingsystem.for_reality_shows.service.ContestantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contestants")
public class ContestantController {

    private final ContestantService contestantService;

    public ContestantController(ContestantService contestantService) {
        this.contestantService = contestantService;
    }

    @PostMapping
    public ResponseEntity<Contestant> createContestant(@RequestBody Contestant contestant) {
        return ResponseEntity.ok(contestantService.createContestant(contestant));
    }

    @GetMapping
    public ResponseEntity<List<Contestant>> getAllContestants() {
        return ResponseEntity.ok(contestantService.getAllContestants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contestant> getContestantById(@PathVariable Long id) {
        return contestantService.getContestantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<Contestant>> getContestantsByShow(@PathVariable Long showId) {
        return ResponseEntity.ok(contestantService.getContestantsByShow(showId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contestant> updateContestant(@PathVariable Long id, @RequestBody Contestant updatedContestant) {
        try {
            Contestant contestant = contestantService.updateContestant(id, updatedContestant);
            return ResponseEntity.ok(contestant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContestant(@PathVariable Long id) {
        boolean deleted = contestantService.deleteContestant(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
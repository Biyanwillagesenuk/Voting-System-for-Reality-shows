package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.model.Contestant;
import com.votingsystem.for_reality_shows.repository.ContestantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contestants")
public class ContestantController {

    private final ContestantRepository contestantRepository;

    public ContestantController(ContestantRepository contestantRepository) {
        this.contestantRepository = contestantRepository;
    }

    @PostMapping
    public ResponseEntity<Contestant> createContestant(@RequestBody Contestant contestant) {
        return ResponseEntity.ok(contestantRepository.save(contestant));
    }

    @GetMapping
    public ResponseEntity<List<Contestant>> getAllContestants() {
        return ResponseEntity.ok(contestantRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contestant> getContestantById(@PathVariable Long id) {
        return contestantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<Contestant>> getContestantsByShow(@PathVariable Long showId) {
        return ResponseEntity.ok(contestantRepository.findByShowId(showId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contestant> updateContestant(@PathVariable Long id, @RequestBody Contestant updatedContestant) {
        return contestantRepository.findById(id).map(contestant -> {
            contestant.setName(updatedContestant.getName());
            contestant.setAge(updatedContestant.getAge());
            contestant.setBio(updatedContestant.getBio());
            contestant.setPhotoUrl(updatedContestant.getPhotoUrl());
            contestant.setShowId(updatedContestant.getShowId());
            return ResponseEntity.ok(contestantRepository.save(contestant));
        }).orElseGet(() -> ResponseEntity.<Contestant>notFound().build()); // Fixed here
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContestant(@PathVariable Long id) {
        if (!contestantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contestantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
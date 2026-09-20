package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.model.Show;
import com.votingsystem.for_reality_shows.repository.ShowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        return ResponseEntity.ok(showRepository.save(show));
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        return showRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody Show updatedShow) {
        return showRepository.findById(id).map(show -> {
            show.setTitle(updatedShow.getTitle());
            show.setDescription(updatedShow.getDescription());
            show.setStartDate(updatedShow.getStartDate());
            show.setStatus(updatedShow.getStatus());
            return ResponseEntity.ok(showRepository.save(show));
        }).orElseGet(() -> ResponseEntity.<Show>notFound().build()); // Fixed here
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        if (!showRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        showRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
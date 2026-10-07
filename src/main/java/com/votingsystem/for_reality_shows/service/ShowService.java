package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.model.Show;
import com.votingsystem.for_reality_shows.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show createShow(Show show) {
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Optional<Show> getShowById(Long id) {
        return showRepository.findById(id);
    }

    public Show updateShow(Long id, Show updatedShow) {
        return showRepository.findById(id).map(show -> {
            show.setTitle(updatedShow.getTitle());
            show.setDescription(updatedShow.getDescription());
            show.setStartDate(updatedShow.getStartDate());
            show.setStatus(updatedShow.getStatus());
            return showRepository.save(show);
        }).orElseThrow(() -> new RuntimeException("Show not found with id " + id));
    }

    public boolean deleteShow(Long id) {
        if (!showRepository.existsById(id)) {
            return false;
        }
        showRepository.deleteById(id);
        return true;
    }
}
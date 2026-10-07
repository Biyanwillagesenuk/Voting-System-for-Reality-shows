package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.model.Contestant;
import com.votingsystem.for_reality_shows.repository.ContestantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContestantService {

    private final ContestantRepository contestantRepository;

    public ContestantService(ContestantRepository contestantRepository) {
        this.contestantRepository = contestantRepository;
    }

    public Contestant createContestant(Contestant contestant) {
        return contestantRepository.save(contestant);
    }

    public List<Contestant> getAllContestants() {
        return contestantRepository.findAll();
    }

    public Optional<Contestant> getContestantById(Long id) {
        return contestantRepository.findById(id);
    }

    public List<Contestant> getContestantsByShow(Long showId) {
        return contestantRepository.findByShowId(showId);
    }

    public Contestant updateContestant(Long id, Contestant updatedContestant) {
        return contestantRepository.findById(id).map(contestant -> {
            contestant.setName(updatedContestant.getName());
            contestant.setAge(updatedContestant.getAge());
            contestant.setBio(updatedContestant.getBio());
            contestant.setPhotoUrl(updatedContestant.getPhotoUrl());
            contestant.setShowId(updatedContestant.getShowId());
            return contestantRepository.save(contestant);
        }).orElseThrow(() -> new RuntimeException("Contestant not found with id " + id));
    }

    public boolean deleteContestant(Long id) {
        if (!contestantRepository.existsById(id)) {
            return false;
        }
        contestantRepository.deleteById(id);
        return true;
    }
}

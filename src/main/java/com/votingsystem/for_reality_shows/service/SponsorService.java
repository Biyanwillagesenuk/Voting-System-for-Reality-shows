package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.model.Sponsor;
import com.votingsystem.for_reality_shows.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    public List<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    public List<Sponsor> getSponsorsByShow(Long showId) {
        return sponsorRepository.findByShowId(showId);
    }

    public Optional<Sponsor> getSponsorById(Long id) {
        return sponsorRepository.findById(id);
    }

    public Sponsor saveSponsor(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    public void deleteSponsor(Long id) {
        sponsorRepository.deleteById(id);
    }
}

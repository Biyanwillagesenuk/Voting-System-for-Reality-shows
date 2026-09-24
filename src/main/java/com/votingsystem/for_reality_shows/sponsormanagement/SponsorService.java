package com.votingsystem.for_reality_shows.sponsormanagement;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SponsorService {

    private final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public List<Sponsor> getAll() {
        return sponsorRepository.findAll();
    }

    public List<Sponsor> getActiveSponsors() {
        return sponsorRepository.findByActiveTrue();
    }

    public Sponsor getById(Long id) {
        return sponsorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sponsor not found with id: " + id));
    }

    public Sponsor save(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    public void deactivate(Long id) {
        Sponsor sponsor = getById(id);
        sponsor.setActive(false);
        sponsorRepository.save(sponsor);
    }

    public void delete(Long id) {
        sponsorRepository.deleteById(id);
    }
}

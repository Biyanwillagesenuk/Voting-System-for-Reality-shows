package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.model.Sponsor;
import com.votingsystem.for_reality_shows.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
@CrossOrigin(origins = "*")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @GetMapping
    public List<Sponsor> getAllSponsors(@RequestParam(required = false) Long showId) {
        if (showId != null) {
            return sponsorService.getSponsorsByShow(showId);
        }
        return sponsorService.getAllSponsors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable Long id) {
        return sponsorService.getSponsorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sponsor createSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.saveSponsor(sponsor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sponsor> updateSponsor(@PathVariable Long id, @RequestBody Sponsor sponsorDetails) {
        return sponsorService.getSponsorById(id).map(sponsor -> {
            sponsor.setName(sponsorDetails.getName());
            sponsor.setEmail(sponsorDetails.getEmail());
            sponsor.setCompanyName(sponsorDetails.getCompanyName());
            sponsor.setTier(sponsorDetails.getTier());
            sponsor.setPaymentStatus(sponsorDetails.getPaymentStatus());
            sponsor.setContributionAmount(sponsorDetails.getContributionAmount());
            sponsor.setShowId(sponsorDetails.getShowId());
            return ResponseEntity.ok(sponsorService.saveSponsor(sponsor));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        if (sponsorService.getSponsorById(id).isPresent()) {
            sponsorService.deleteSponsor(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

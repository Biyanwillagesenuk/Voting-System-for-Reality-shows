package com.votingsystem.for_reality_shows.sponsormanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {

    List<Sponsor> findByActiveTrue();

    List<Sponsor> findByTier(SponsorTier tier);
}

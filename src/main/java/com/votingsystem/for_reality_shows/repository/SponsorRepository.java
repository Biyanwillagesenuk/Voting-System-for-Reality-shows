package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    List<Sponsor> findByShowId(Long showId);
}

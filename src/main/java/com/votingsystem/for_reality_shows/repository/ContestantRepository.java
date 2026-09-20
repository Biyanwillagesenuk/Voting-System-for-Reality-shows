package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContestantRepository extends JpaRepository<Contestant, Long> {
    List<Contestant> findByShowId(Long showId);
}

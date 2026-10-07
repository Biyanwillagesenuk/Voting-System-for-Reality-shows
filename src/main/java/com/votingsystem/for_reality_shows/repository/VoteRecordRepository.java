package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.Vote; // Using your Vote entity model
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends JpaRepository<Vote, Long> {

    boolean existsByUserIdAndShowId(Long userId, Long showId);

    List<Vote> findByUserId(Long userId);

    List<Vote> findByShowId(Long showId);

    List<Vote> findByContestantId(Long contestantId);
}

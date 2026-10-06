package com.group07.repository;

import com.group07.entity.ResultSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<ResultSummary, Long> {
    List<ResultSummary> findByEpisodeIdOrderByFinalScoreDesc(Long episodeId);
    Optional<ResultSummary> findByCandidateIdAndEpisodeId(Long candidateId, Long episodeId);

    // NEW FEATURE: Deletes a specific contestant record from a single episode context
    @Modifying
    @Transactional
    void deleteByCandidateIdAndEpisodeId(Long candidateId, Long episodeId);

    void deleteByEpisodeId(Long episodeId);
}


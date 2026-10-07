package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.ResultSummary;
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

    @Modifying
    @Transactional
    void deleteByCandidateIdAndEpisodeId(Long candidateId, Long episodeId);

    void deleteByEpisodeId(Long episodeId);
}

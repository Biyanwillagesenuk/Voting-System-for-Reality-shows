package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.model.ResultSummary;
import com.votingsystem.for_reality_shows.repository.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoteProcessingService {
    private final ResultRepository resultRepository;

    public VoteProcessingService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Transactional
    public List<ResultSummary> getLiveLeaderboard(Long episodeId) {
        List<ResultSummary> results = resultRepository.findByEpisodeIdOrderByFinalScoreDesc(episodeId);
        for (int i = 0; i < results.size(); i++) {
            results.get(i).setRankPosition(i + 1);
        }
        return resultRepository.saveAll(results);
    }

    @Transactional
    public ResultSummary updateJudgeScore(Long candidateId, Long episodeId, Double newJudgeScore) {
        if (candidateId == null || candidateId < 1 || candidateId > 100) {
            throw new IllegalArgumentException("Contestant ID must be between 1 and 100.");
        }
        if (newJudgeScore == null || newJudgeScore < 1.0 || newJudgeScore > 10.0) {
            throw new IllegalArgumentException("Judge score must be between 1.0 and 10.0.");
        }

        ResultSummary result = resultRepository.findByCandidateIdAndEpisodeId(candidateId, episodeId)
                .orElseGet(() -> ResultSummary.builder()
                        .candidateId(candidateId)
                        .candidateName("Contestant " + candidateId)
                        .episodeId(episodeId)
                        .publicVotes(1000)
                        .judgeScore(newJudgeScore)
                        .build());

        result.setJudgeScore(newJudgeScore);
        return resultRepository.save(result);
    }

    @Transactional
    public void deleteCandidateData(Long candidateId, Long episodeId) {
        resultRepository.deleteByCandidateIdAndEpisodeId(candidateId, episodeId);
    }

    public String generateEpisodeReport(Long episodeId) {
        List<ResultSummary> rankedList = getLiveLeaderboard(episodeId);
        if (rankedList.isEmpty()) {
            return "No vote data available for Episode " + episodeId;
        }

        ResultSummary topContestant = rankedList.get(0);
        ResultSummary lowestContestant = rankedList.get(rankedList.size() - 1);

        return String.format("OFFICIAL REPORT [Episode %d]: Winner = %s (Score: %.2f) | Elimination Risk = %s (Score: %.2f)",
                episodeId, topContestant.getCandidateName(), topContestant.getFinalScore(),
                lowestContestant.getCandidateName(), lowestContestant.getFinalScore());
    }

    @Transactional
    public void purgeEpisodeData(Long episodeId) {
        resultRepository.deleteByEpisodeId(episodeId);
    }
}

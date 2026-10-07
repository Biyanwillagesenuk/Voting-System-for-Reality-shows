package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.model.ResultSummary;
import com.votingsystem.for_reality_shows.service.VoteProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/results")
@Validated
public class ResultsController {
    private final VoteProcessingService voteProcessingService;

    public ResultsController(VoteProcessingService voteProcessingService) {
        this.voteProcessingService = voteProcessingService;
    }

    @PostMapping("/reports")
    public ResponseEntity<String> generateReport(@RequestParam Long episodeId) {
        String report = voteProcessingService.generateEpisodeReport(episodeId);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<ResultSummary>> getLiveStatistics(@RequestParam Long episodeId) {
        List<ResultSummary> leaderboard = voteProcessingService.getLiveLeaderboard(episodeId);
        return ResponseEntity.ok(leaderboard);
    }

    @PutMapping("/scores/{candidateId}")
    public ResponseEntity<ResultSummary> updateJudgeScore(
            @PathVariable @Min(1) @Max(100) Long candidateId,
            @RequestParam Long episodeId,
            @RequestParam @Min(1) @Max(10) Double judgeScore) {
        ResultSummary updated = voteProcessingService.updateJudgeScore(candidateId, episodeId, judgeScore);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/candidate/{candidateId}/purge/{episodeId}")
    public ResponseEntity<String> deleteIndividualCandidate(
            @PathVariable Long candidateId,
            @PathVariable Long episodeId) {
        voteProcessingService.deleteCandidateData(candidateId, episodeId);
        return ResponseEntity.ok("Contestant " + candidateId + " data deleted successfully from Episode " + episodeId);
    }

    @DeleteMapping("/purge/{episodeId}")
    public ResponseEntity<String> purgeEpisodeVotes(@PathVariable Long episodeId) {
        voteProcessingService.purgeEpisodeData(episodeId);
        return ResponseEntity.ok("Episode " + episodeId + " details purged successfully.");
    }
}

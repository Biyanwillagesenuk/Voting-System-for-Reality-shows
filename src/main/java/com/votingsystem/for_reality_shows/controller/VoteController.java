package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.dto.ApiResponse;
import com.votingsystem.for_reality_shows.dto.VoteRequest;
import com.votingsystem.for_reality_shows.dto.VoteResponse;
import com.votingsystem.for_reality_shows.service.VoteService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<VoteResponse>> castVote(@Valid @RequestBody VoteRequest request) {
        VoteResponse response = voteService.castVote(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Vote submitted successfully!", response));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VoteResponse>> getVoteById(@PathVariable("id") Long id) {
        VoteResponse response = voteService.getVoteById(id);
        return ResponseEntity.ok(ApiResponse.success("Vote retrieved successfully", response));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<VoteResponse>>> getVotesByUser(@PathVariable("userId") Long userId) {
        List<VoteResponse> responses = voteService.getVotesByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("User votes retrieved successfully", responses));
    }


    @GetMapping("/show/{showId}")
    public ResponseEntity<ApiResponse<List<VoteResponse>>> getVotesByShow(@PathVariable("showId") Long showId) {
        List<VoteResponse> responses = voteService.getVotesByShowId(showId);
        return ResponseEntity.ok(ApiResponse.success("Show votes retrieved successfully", responses));
    }


    @GetMapping("/contestant/{contestantId}")
    public ResponseEntity<ApiResponse<List<VoteResponse>>> getVotesByContestant(@PathVariable("contestantId") Long contestantId) {
        List<VoteResponse> responses = voteService.getVotesByContestantId(contestantId);
        return ResponseEntity.ok(ApiResponse.success("Contestant votes retrieved successfully", responses));
    }


    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkVoteStatus(
            @RequestParam("userId") Long userId,
            @RequestParam("showId") Long showId) {
        boolean hasVoted = voteService.hasUserVotedInShow(userId, showId);
        return ResponseEntity.ok(ApiResponse.success(
                "Vote status checked",
                Map.of("userId", userId, "showId", showId, "hasVoted", hasVoted)
        ));
    }
}

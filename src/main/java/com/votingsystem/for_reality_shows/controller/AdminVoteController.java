package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.dto.ApiResponse;
import com.votingsystem.for_reality_shows.dto.VoteResponse;
import com.votingsystem.for_reality_shows.dto.VoteStatusUpdateRequest;
import com.votingsystem.for_reality_shows.exception.UnauthorizedException;
import com.votingsystem.for_reality_shows.service.VoteService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/votes")
public class AdminVoteController {

    private final VoteService voteService;

    public AdminVoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<VoteResponse>> updateVoteStatus(
            @PathVariable("id") Long id,
            @RequestHeader(value = "X-User-Role", defaultValue = "USER") String role,
            @Valid @RequestBody VoteStatusUpdateRequest request) {

        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new UnauthorizedException("Administrative privileges required. Role '" + role + "' is not permitted.");
        }

        VoteResponse response = voteService.updateVoteStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Vote status updated successfully", response));
    }
}

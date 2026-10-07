package com.votingsystem.for_reality_shows.dto;

import jakarta.validation.constraints.NotNull;

public class VoteRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Contestant ID is required")
    private Long contestantId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public Long getContestantId() { return contestantId; }
    public void setContestantId(Long contestantId) { this.contestantId = contestantId; }
}

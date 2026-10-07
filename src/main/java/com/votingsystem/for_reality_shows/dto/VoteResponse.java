package com.votingsystem.for_reality_shows.dto;

import com.votingsystem.for_reality_shows.model.Vote;
import com.votingsystem.for_reality_shows.model.VoteStatus;
import java.time.LocalDateTime;

public class VoteResponse {
    private Long voteId;
    private Long userId;
    private Long showId;
    private Long contestantId;
    private LocalDateTime votedAt;
    private VoteStatus status;

    public VoteResponse(Vote vote) {
        this.voteId = vote.getVoteId(); // Changed from getVoteId() to getId()
        this.userId = vote.getUserId();
        this.showId = vote.getShowId();
        this.contestantId = vote.getContestantId();
        this.votedAt = vote.getVotedAt();
        this.status = vote.getStatus();
    }

    public Long getVoteId() { return voteId; }
    public Long getUserId() { return userId; }
    public Long getShowId() { return showId; }
    public Long getContestantId() { return contestantId; }
    public LocalDateTime getVotedAt() { return votedAt; }
    public VoteStatus getStatus() { return status; }
}

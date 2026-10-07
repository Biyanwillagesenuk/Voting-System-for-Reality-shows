package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "votes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_user_show", columnNames = {"user_id", "show_id"})
        },
        indexes = {
                @Index(name = "idx_votes_user_id", columnList = "user_id"),
                @Index(name = "idx_votes_show_id", columnList = "show_id"),
                @Index(name = "idx_votes_contestant_id", columnList = "contestant_id")
        }
)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id", nullable = false, updatable = false)
    private Long voteId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "show_id", nullable = false, updatable = false)
    private Long showId;

    @Column(name = "contestant_id", nullable = false, updatable = false)
    private Long contestantId;

    @Column(name = "voted_at", nullable = false, updatable = false)
    private LocalDateTime votedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private VoteStatus status;

    public Vote() {
    }

    public Vote(Long userId, Long showId, Long contestantId, LocalDateTime votedAt, VoteStatus status) {
        this.userId = userId;
        this.showId = showId;
        this.contestantId = contestantId;
        this.votedAt = votedAt;
        this.status = status;
    }

    public Vote(Long voteId, Long userId, Long showId, Long contestantId, LocalDateTime votedAt, VoteStatus status) {
        this.voteId = voteId;
        this.userId = userId;
        this.showId = showId;
        this.contestantId = contestantId;
        this.votedAt = votedAt;
        this.status = status;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Long getContestantId() {
        return contestantId;
    }

    public void setContestantId(Long contestantId) {
        this.contestantId = contestantId;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }

    public VoteStatus getStatus() {
        return status;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(voteId, vote.voteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteId);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                ", userId=" + userId +
                ", showId=" + showId +
                ", contestantId=" + contestantId +
                ", votedAt=" + votedAt +
                ", status=" + status +
                '}';
    }
}


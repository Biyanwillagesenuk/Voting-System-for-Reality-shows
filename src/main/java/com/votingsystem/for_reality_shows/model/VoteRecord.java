package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote_records", uniqueConstraints = {
        // This strictly enforces: One User can only cast One Vote per Episode
        @UniqueConstraint(columnNames = {"user_id", "episode_id"})
})
public class VoteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "contestant_id", nullable = false)
    private Long contestantId;

    @Column(name = "episode_id", nullable = false)
    private Long episodeId;

    @Column(name = "show_id", nullable = false)
    private Long showId;

    private Double score; // 1.0 for Voters, 1.0-10.0 for Judges

    private LocalDateTime votedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContestantId() {
        return contestantId;
    }

    public void setContestantId(Long contestantId) {
        this.contestantId = contestantId;
    }

    public Long getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }
}

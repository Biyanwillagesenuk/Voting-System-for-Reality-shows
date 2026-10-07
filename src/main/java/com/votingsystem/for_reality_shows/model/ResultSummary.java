package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "result_summaries")
public class ResultSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Contestant ID is required")
    @Min(value = 1, message = "Contestant ID must be at least 1")
    @Max(value = 100, message = "Contestant ID cannot exceed 100")
    private Long candidateId;

    private String candidateName;
    private Long episodeId;
    private Integer publicVotes;

    @NotNull(message = "Judge score is required")
    @Min(value = 1, message = "Judge score must be at least 1.0")
    @Max(value = 10, message = "Judge score cannot exceed 10.0")
    private Double judgeScore;

    private Double finalScore;
    private Integer rankPosition;

    public ResultSummary() {}

    private ResultSummary(Builder builder) {
        this.id = builder.id;
        this.candidateId = builder.candidateId;
        this.candidateName = builder.candidateName;
        this.episodeId = builder.episodeId;
        this.publicVotes = builder.publicVotes;
        this.judgeScore = builder.judgeScore;
        this.rankPosition = builder.rankPosition;
        calculateFinalScore();
    }

    // --- BUILDER PATTERN (Creational) ---
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Long candidateId;
        private String candidateName;
        private Long episodeId;
        private Integer publicVotes;
        private Double judgeScore;
        private Integer rankPosition;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder candidateId(Long candidateId) {
            this.candidateId = candidateId;
            return this;
        }

        public Builder candidateName(String candidateName) {
            this.candidateName = candidateName;
            return this;
        }

        public Builder episodeId(Long episodeId) {
            this.episodeId = episodeId;
            return this;
        }

        public Builder publicVotes(Integer publicVotes) {
            this.publicVotes = publicVotes;
            return this;
        }

        public Builder judgeScore(Double judgeScore) {
            this.judgeScore = judgeScore;
            return this;
        }

        public Builder rankPosition(Integer rankPosition) {
            this.rankPosition = rankPosition;
            return this;
        }

        public ResultSummary build() {
            return new ResultSummary(this);
        }
    }

    public void calculateFinalScore() {
        this.finalScore = (this.publicVotes != null ? this.publicVotes * 0.6 : 0)
                + (this.judgeScore != null ? this.judgeScore * 0.4 : 0);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public Long getEpisodeId() { return episodeId; }
    public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }

    public Integer getPublicVotes() { return publicVotes; }
    public void setPublicVotes(Integer publicVotes) { this.publicVotes = publicVotes; calculateFinalScore(); }

    public Double getJudgeScore() { return judgeScore; }
    public void setJudgeScore(Double judgeScore) { this.judgeScore = judgeScore; calculateFinalScore(); }

    public Double getFinalScore() { return finalScore; }

    public Integer getRankPosition() { return rankPosition; }
    public void setRankPosition(Integer rankPosition) { this.rankPosition = rankPosition; }
}

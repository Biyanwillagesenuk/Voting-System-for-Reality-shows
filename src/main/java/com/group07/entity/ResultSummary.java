package com.group07.entity;
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

    // VALIDATION: No negative numbers, strictly restricted between 1 and 100
    @NotNull(message = "Contestant ID is required")
    @Min(value = 1, message = "Contestant ID must be at least 1")
    @Max(value = 100, message = "Contestant ID cannot exceed 100")
    private Long candidateId;

    private String candidateName;
    private Long episodeId;
    private Integer publicVotes;

    // VALIDATION: Restricts judge score decimals between 1.0 and 10.0
    @NotNull(message = "Judge score is required")
    @Min(value = 1, message = "Judge score must be at least 1.0")
    @Max(value = 10, message = "Judge score cannot exceed 10.0")
    private Double judgeScore;

    private Double finalScore;
    private Integer rankPosition;

    public ResultSummary() {}

    public ResultSummary(Long candidateId, String candidateName, Long episodeId, Integer publicVotes, Double judgeScore) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.episodeId = episodeId;
        this.publicVotes = publicVotes;
        this.judgeScore = judgeScore;
        calculateFinalScore();
    }

    public void calculateFinalScore() {
        this.finalScore = (this.publicVotes != null ? this.publicVotes * 0.6 : 0)
                + (this.judgeScore != null ? this.judgeScore * 0.4 : 0);
    }

    public Long getId() { return id; }
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




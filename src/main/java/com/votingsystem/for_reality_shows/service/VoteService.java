package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.dto.VoteRequest;
import com.votingsystem.for_reality_shows.dto.VoteResponse;
import com.votingsystem.for_reality_shows.dto.VoteStatusUpdateRequest;
import com.votingsystem.for_reality_shows.model.Vote;
import com.votingsystem.for_reality_shows.model.VoteStatus;
import com.votingsystem.for_reality_shows.repository.VoteRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRecordRepository voteRecordRepository;

    public VoteService(VoteRecordRepository voteRecordRepository) {
        this.voteRecordRepository = voteRecordRepository;
    }

    @Transactional
    public VoteResponse castVote(VoteRequest request) {
        if (voteRecordRepository.existsByUserIdAndShowId(request.getUserId(), request.getShowId())) {
            throw new IllegalArgumentException("User has already cast a vote in this show.");
        }

        Vote vote = new Vote();
        vote.setUserId(request.getUserId());
        vote.setShowId(request.getShowId());
        vote.setContestantId(request.getContestantId());
        vote.setVotedAt(LocalDateTime.now());
        vote.setStatus(VoteStatus.VALID);

        Vote savedVote = voteRecordRepository.save(vote);
        return new VoteResponse(savedVote);
    }

    public VoteResponse getVoteById(Long id) {
        Vote vote = voteRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found with id: " + id));
        return new VoteResponse(vote);
    }

    public List<VoteResponse> getVotesByUserId(Long userId) {
        return voteRecordRepository.findByUserId(userId).stream()
                .map(VoteResponse::new)
                .collect(Collectors.toList());
    }

    public List<VoteResponse> getVotesByShowId(Long showId) {
        return voteRecordRepository.findByShowId(showId).stream()
                .map(VoteResponse::new)
                .collect(Collectors.toList());
    }

    public List<VoteResponse> getVotesByContestantId(Long contestantId) {
        return voteRecordRepository.findByContestantId(contestantId).stream()
                .map(VoteResponse::new)
                .collect(Collectors.toList());
    }

    public boolean hasUserVotedInShow(Long userId, Long showId) {
        return voteRecordRepository.existsByUserIdAndShowId(userId, showId);
    }

    @Transactional
    public VoteResponse updateVoteStatus(Long voteId, VoteStatusUpdateRequest request) {
        Vote vote = voteRecordRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found with id: " + voteId));

        vote.setStatus(request.getStatus());
        Vote updatedVote = voteRecordRepository.save(vote);
        return new VoteResponse(updatedVote);
    }
}

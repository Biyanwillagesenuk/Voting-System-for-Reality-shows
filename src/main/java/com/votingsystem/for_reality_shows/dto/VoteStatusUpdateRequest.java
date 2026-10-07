package com.votingsystem.for_reality_shows.dto;

import com.votingsystem.for_reality_shows.model.VoteStatus; // Change 'entity' to 'model'
import jakarta.validation.constraints.NotNull;

public class VoteStatusUpdateRequest {

    @NotNull(message = "New vote status is required")
    private VoteStatus status;

    public VoteStatusUpdateRequest() {
    }

    public VoteStatus getStatus() {
        return status;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }
}

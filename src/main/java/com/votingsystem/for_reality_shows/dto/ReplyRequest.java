package com.votingsystem.for_reality_shows.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReplyRequest {
    private Long responderUserId;

    @NotBlank(message = "Message cannot be empty")
    private String message;

    private boolean isInternalNote;
}

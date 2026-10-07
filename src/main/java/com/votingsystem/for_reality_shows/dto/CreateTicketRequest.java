package com.votingsystem.for_reality_shows.dto;

import com.votingsystem.for_reality_shows.model.TicketType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTicketRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private TicketType type;
    private Long submittedByUserId;
}

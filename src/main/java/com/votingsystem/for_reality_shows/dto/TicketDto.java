package com.votingsystem.for_reality_shows.dto;

import com.votingsystem.for_reality_shows.model.TicketStatus;
import com.votingsystem.for_reality_shows.model.TicketType;
import lombok.Data;

@Data
public class TicketDto {
    private Long id;
    private String ticketRef;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketType type;
    private Long submittedByUserId;
}

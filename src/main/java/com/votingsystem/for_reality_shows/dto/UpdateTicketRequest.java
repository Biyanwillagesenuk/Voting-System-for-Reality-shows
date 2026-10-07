package com.votingsystem.for_reality_shows.dto;

import com.votingsystem.for_reality_shows.model.TicketStatus;
import lombok.Data;

@Data
public class UpdateTicketRequest {
    private TicketStatus status;
}

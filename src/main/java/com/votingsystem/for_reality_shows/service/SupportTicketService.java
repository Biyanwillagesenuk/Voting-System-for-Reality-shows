package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.dto.*;
import com.votingsystem.for_reality_shows.model.TicketStatus;
import com.votingsystem.for_reality_shows.model.TicketType;

import java.util.List;

/** Contract for all support-ticket operations used by the controller. */
public interface SupportTicketService {

    // Create
    TicketDto createTicket(CreateTicketRequest request);

    // Read
    TicketDto getTicketByRef(String ticketRef);

    TicketDto getTicketById(Long id);

    List<TicketDto> getAllTickets();

    List<TicketDto> getTicketsByStatus(TicketStatus status);

    List<TicketDto> getTicketsByType(TicketType type);

    List<TicketDto> getTicketsByStatusAndType(TicketStatus status, TicketType type);

    List<TicketDto> getTicketsForUser(Long userId);

    // Update
    TicketDto updateTicket(Long id, UpdateTicketRequest request);

    TicketDto addReply(Long id, ReplyRequest request);

    // Delete
    void deleteTicket(Long id);

    // Dashboard counters
    TicketStatsDto getStats();
}

package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.dto.*;
import com.votingsystem.for_reality_shows.exception.ResourceNotFoundException;
import com.votingsystem.for_reality_shows.model.*;
import com.votingsystem.for_reality_shows.repository.SupportTicketRepository;
import com.votingsystem.for_reality_shows.repository.TicketResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportTicketService {

    private final SupportTicketRepository ticketRepo;
    private final TicketResponseRepository responseRepo;

    public TicketDto createTicket(CreateTicketRequest request) {
        SupportTicket ticket = SupportTicket.builder()
                .ticketRef("TCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType() != null ? request.getType() : TicketType.INQUIRY)
                .status(TicketStatus.OPEN)
                .submittedByUserId(request.getSubmittedByUserId())
                .createdAt(LocalDateTime.now())
                .build();
        return mapToDto(ticketRepo.save(ticket));
    }

    public List<TicketDto> getAllTickets() {
        return ticketRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByStatusAndType(TicketStatus status, TicketType type) {
        return ticketRepo.findByStatusAndType(status, type).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByStatus(TicketStatus status) {
        return ticketRepo.findByStatus(status).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<TicketDto> getTicketsByType(TicketType type) {
        return ticketRepo.findByType(type).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public TicketDto getTicketById(Long id) {
        return mapToDto(ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id)));
    }

    public TicketDto getTicketByRef(String ticketRef) {
        return mapToDto(ticketRepo.findByTicketRef(ticketRef)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with Ref: " + ticketRef)));
    }

    public List<TicketDto> getTicketsForUser(Long userId) {
        return ticketRepo.findBySubmittedByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public TicketDto updateTicket(Long id, UpdateTicketRequest request) {
        SupportTicket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }
        ticket.setUpdatedAt(LocalDateTime.now());
        return mapToDto(ticketRepo.save(ticket));
    }

    public TicketDto addReply(Long ticketId, ReplyRequest request) {
        SupportTicket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        TicketResponse response = TicketResponse.builder()
                .ticket(ticket)
                .responderUserId(request.getResponderUserId())
                .message(request.getMessage())
                .isInternalNote(request.isInternalNote())
                .createdAt(LocalDateTime.now())
                .build();

        responseRepo.save(response);
        ticket.setUpdatedAt(LocalDateTime.now());
        return mapToDto(ticketRepo.save(ticket));
    }

    public void deleteTicket(Long id) {
        if (!ticketRepo.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found");
        }
        ticketRepo.deleteById(id);
    }

    public TicketStatsDto getStats() {
        TicketStatsDto stats = new TicketStatsDto();
        stats.setOpenTickets(ticketRepo.countByStatus(TicketStatus.OPEN));
        stats.setActiveComplaints(ticketRepo.countByTypeInAndStatusNot(
                List.of(TicketType.COMPLAINT), TicketStatus.CLOSED));
        return stats;
    }

    private TicketDto mapToDto(SupportTicket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setTicketRef(ticket.getTicketRef());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setStatus(ticket.getStatus());
        dto.setType(ticket.getType());
        dto.setSubmittedByUserId(ticket.getSubmittedByUserId());
        return dto;
    }
}

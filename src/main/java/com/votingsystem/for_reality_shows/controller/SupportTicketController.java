package com.votingsystem.for_reality_shows.controller;

import com.votingsystem.for_reality_shows.dto.*;
import com.votingsystem.for_reality_shows.model.TicketStatus;
import com.votingsystem.for_reality_shows.model.TicketType;
import com.votingsystem.for_reality_shows.service.SupportTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support/tickets")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService ticketService;

    // CREATE
    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        TicketDto created = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ
    @GetMapping
    public ResponseEntity<List<TicketDto>> getTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketType type) {

        if (status != null && type != null) {
            return ResponseEntity.ok(ticketService.getTicketsByStatusAndType(status, type));
        }
        if (status != null) {
            return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
        }
        if (type != null) {
            return ResponseEntity.ok(ticketService.getTicketsByType(type));
        }
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/stats")
    public ResponseEntity<TicketStatsDto> getStats() {
        return ResponseEntity.ok(ticketService.getStats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/ref/{ticketRef}")
    public ResponseEntity<TicketDto> getTicketByRef(@PathVariable String ticketRef) {
        return ResponseEntity.ok(ticketService.getTicketByRef(ticketRef));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> getTicketsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsForUser(userId));
    }

    // UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long id,
                                                  @RequestBody UpdateTicketRequest request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }

    @PostMapping("/{id}/replies")
    public ResponseEntity<TicketDto> addReply(@PathVariable Long id,
                                              @Valid @RequestBody ReplyRequest request) {
        return ResponseEntity.ok(ticketService.addReply(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}

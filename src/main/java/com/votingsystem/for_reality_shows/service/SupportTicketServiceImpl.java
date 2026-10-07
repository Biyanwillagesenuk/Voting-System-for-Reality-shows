package com.votingsystem.for_reality_shows.service;

import com.votingsystem.for_reality_shows.dto.*;
import com.votingsystem.for_reality_shows.exception.ResourceNotFoundException;
import com.votingsystem.for_reality_shows.model.*;
import com.votingsystem.for_reality_shows.repository.SupportTicketRepository;
import com.votingsystem.for_reality_shows.repository.TicketResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for support tickets.
 * Every public method runs in a transaction (read-only for queries).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SupportTicketServiceImpl implements SupportTicketService {

    // Database access (injected by Lombok constructor)
    private final SupportTicketRepository ticketRepository;
    private final TicketResponseRepository responseRepository;

    /** Creates a new ticket (with a duplicate guard) and returns it with its public reference. */
    @Override
    public TicketDto createTicket(CreateTicketRequest request) {
        // Duplicate guard: if the same user just sent the exact same ticket
        // (double click, retry, two tabs), return the existing one instead of a new row.
        var duplicate = ticketRepository
                .findFirstBySubmittedByUserIdAndSubjectAndDescriptionAndCreatedAtAfter(
                        request.getSubmittedByUserId(),
                        request.getSubject(),
                        request.getDescription(),
                        LocalDateTime.now().minusSeconds(10));
        if (duplicate.isPresent()) {
            return TicketDto.from(duplicate.get());
        }

        // ticket_ref is NOT NULL, so give the first INSERT a temporary
        // unique reference. After the database assigns the numeric ID,
        // replace it with the public TCK-000001 style reference.
        String temporaryRef = "TCK-TMP-" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 12);

        SupportTicket ticket = SupportTicket.builder()
                .ticketRef(temporaryRef)
                .type(request.getType())
                .reporterRole(request.getReporterRole())
                .submittedByUserId(request.getSubmittedByUserId())
                .submittedByName(request.getSubmittedByName())
                .subject(request.getSubject())
                .description(request.getDescription())
                .reportedSubject(request.getReportedSubject())
                .evidenceLinks(request.getEvidenceLinks())
                .status(TicketStatus.OPEN)
                .priority(resolvePriority(request))
                .build();

        // Persist once so the DB assigns an ID, then build the readable reference from it.
        // The entity is managed inside this transaction, so the new ref is written
        // automatically on commit - no second save() call is needed.
        SupportTicket saved = ticketRepository.save(ticket);
        saved.setTicketRef(generateTicketRef(saved.getId()));

        return TicketDto.from(saved);
    }

    /**
     * Harassment and impersonation reports default to HIGH priority
     * unless the submitter/staff explicitly set something else,
     * since these directly affect a contestant's safety and the
     * fairness of the vote.
     */
    private TicketPriority resolvePriority(CreateTicketRequest request) {
        if (request.getPriority() != null) return request.getPriority();
        boolean isAbuseReport = request.getType() == TicketType.HARASSMENT_REPORT
                || request.getType() == TicketType.IMPERSONATION_REPORT;
        return isAbuseReport ? TicketPriority.HIGH : TicketPriority.MEDIUM;
    }

    /** Turns a numeric id into the public reference, e.g. 12 -> TCK-000012. */
    private String generateTicketRef(Long id) {
        return String.format("TCK-%06d", id);
    }

    /** Finds one ticket by its public reference (TCK-000001). */
    @Override
    @Transactional(readOnly = true)
    public TicketDto getTicketByRef(String ticketRef) {
        return TicketDto.from(findByRefOrThrow(ticketRef));
    }

    /** Finds one ticket by its internal id. */
    @Override
    @Transactional(readOnly = true)
    public TicketDto getTicketById(Long id) {
        return TicketDto.from(findByIdOrThrow(id));
    }

    /** Returns every ticket, newest first (used by the staff dashboard). */
    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(TicketDto::from)
                .collect(Collectors.toList());
    }

    /** Filter: tickets with one status. */
    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status).stream()
                .map(TicketDto::from).collect(Collectors.toList());
    }

    /** Filter: tickets of one type. */
    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getTicketsByType(TicketType type) {
        return ticketRepository.findByType(type).stream()
                .map(TicketDto::from).collect(Collectors.toList());
    }

    /** Filter: tickets matching both a status and a type. */
    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getTicketsByStatusAndType(TicketStatus status, TicketType type) {
        return ticketRepository.findByStatusAndType(status, type).stream()
                .map(TicketDto::from)
                .collect(Collectors.toList());
    }

    /** All tickets raised by one user ("my tickets"). */
    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getTicketsForUser(Long userId) {
        return ticketRepository.findBySubmittedByUserId(userId).stream()
                .map(TicketDto::from).collect(Collectors.toList());
    }

    /** PATCH-style update: only the fields that are sent get changed. */
    @Override
    public TicketDto updateTicket(Long id, UpdateTicketRequest request) {
        SupportTicket ticket = findByIdOrThrow(id);

        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
            if (request.getStatus() == TicketStatus.RESOLVED && ticket.getResolvedAt() == null) {
                ticket.setResolvedAt(LocalDateTime.now());
            }
        }
        if (request.getPriority() != null) {
            ticket.setPriority(request.getPriority());
        }
        if (request.getAssignedTo() != null) {
            ticket.setAssignedTo(request.getAssignedTo());
        }

        return TicketDto.from(ticketRepository.save(ticket));
    }

    /** Adds a staff reply or internal note to a ticket. */
    @Override
    public TicketDto addReply(Long id, ReplyRequest request) {
        SupportTicket ticket = findByIdOrThrow(id);

        TicketResponse response = TicketResponse.builder()
                .ticket(ticket)
                .respondedBy(request.getRespondedBy())
                .message(request.getMessage())
                .internalNote(request.isInternalNote())
                .build();

        responseRepository.save(response);

        // Replying to an OPEN ticket automatically moves it to IN_PROGRESS,
        // unless it's just an internal note.
        if (!request.isInternalNote() && ticket.getStatus() == TicketStatus.OPEN) {
            ticket.setStatus(TicketStatus.IN_PROGRESS);
            ticketRepository.save(ticket);
        }

        return TicketDto.from(findByIdOrThrow(id));
    }

    /** Deletes a ticket (and its replies) or fails if it does not exist. */
    @Override
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    /** Counters for the dashboard cards. */
    @Override
    @Transactional(readOnly = true)
    public TicketStatsDto getStats() {
        return TicketStatsDto.builder()
                .open(ticketRepository.countByStatus(TicketStatus.OPEN))
                .inProgress(ticketRepository.countByStatus(TicketStatus.IN_PROGRESS))
                .resolved(ticketRepository.countByStatus(TicketStatus.RESOLVED))
                .closed(ticketRepository.countByStatus(TicketStatus.CLOSED))
                .openAbuseReports(ticketRepository.countByTypeInAndStatusNot(
                        List.of(TicketType.HARASSMENT_REPORT, TicketType.IMPERSONATION_REPORT),
                        TicketStatus.CLOSED))
                .total(ticketRepository.count())
                .build();
    }

    /** Loads a ticket by id or throws 404. */
    private SupportTicket findByIdOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    /** Loads a ticket by public reference or throws 404. */
    private SupportTicket findByRefOrThrow(String ref) {
        return ticketRepository.findByTicketRef(ref)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ref: " + ref));
    }
}

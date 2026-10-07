package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private SupportTicket ticket;

    private Long responderUserId;

    @Column(columnDefinition = "TEXT")
    private String message;

    private boolean isInternalNote;
    private LocalDateTime createdAt;
}

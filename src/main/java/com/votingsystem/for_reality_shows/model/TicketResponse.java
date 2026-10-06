package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * One message in a ticket's reply thread. Either the support staff
 * replying to the submitter, or an internal note visible only to staff.
 */
@Entity
@Table(name = "ticket_responses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private SupportTicket ticket;

    /** Name of the staff member who wrote this reply */
    @Column(nullable = false, length = 120)
    private String respondedBy;

    @Column(nullable = false, length = 2000)
    private String message;

    /** true = internal note only staff can see, false = visible to the submitter */
    @Column(nullable = false)
    @Builder.Default
    private boolean internalNote = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

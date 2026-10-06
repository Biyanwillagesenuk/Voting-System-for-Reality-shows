package com.votingsystem.for_reality_shows.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A single support ticket: a complaint, a general inquiry, or a
 * contestant's report of harassment / impersonation.
 *
 * NOTE ON userId / contestantId:
 * This module is built and can run independently of the User Account
 * Management and Contestant & Show Management modules your teammates
 * are building. Rather than a hard JPA @ManyToOne relationship to their
 * entity classes (which would break the build until everything is
 * merged), we store the IDs as plain foreign-key columns. Once the
 * whole app is integrated in Week 12, these can be swapped for real
 * @ManyToOne relations if the team wants stronger referential integrity.
 */
@Entity
@Table(name = "support_tickets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Human-friendly reference shown to the viewer, e.g. TCK-000123 */
    @Column(unique = true, nullable = false, length = 20)
    private String ticketRef;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReporterRole reporterRole;

    /** FK to the user account (viewer or contestant) that raised the ticket */
    @Column(nullable = false)
    private Long submittedByUserId;

    /** Snapshot of the submitter's name so tickets are still readable
     *  even if the user account is later deleted or renamed */
    @Column(nullable = false, length = 120)
    private String submittedByName;

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(nullable = false, length = 4000)
    private String description;

    /**
     * Only relevant for HARASSMENT_REPORT / IMPERSONATION_REPORT:
     * who/what the contestant is reporting against, plus links to
     * evidence (screenshots, profile URLs, post links etc). We keep
     * this as free text / comma-separated URLs rather than binary
     * file storage, matching the project's Week 3-14 prototype scope.
     */
    @Column(length = 200)
    private String reportedSubject;

    @Column(length = 1000)
    private String evidenceLinks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private TicketPriority priority = TicketPriority.MEDIUM;

    /** Support staff member currently handling this ticket (nullable = unassigned) */
    @Column(length = 120)
    private String assignedTo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;

    /** Full reply thread between staff and the submitter */
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<TicketResponse> responses = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) this.status = TicketStatus.OPEN;
        if (this.priority == null) this.priority = TicketPriority.MEDIUM;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

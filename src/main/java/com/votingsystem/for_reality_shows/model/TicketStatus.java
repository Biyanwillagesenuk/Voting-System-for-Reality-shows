package com.votingsystem.for_reality_shows.model;

/**
 * Lifecycle status of a support ticket.
 *
 * OPEN         -> just submitted, nobody has picked it up yet
 * IN_PROGRESS  -> a staff member is actively working on it
 * RESOLVED     -> staff believes the issue is fixed / answered
 * CLOSED       -> confirmed done, kept only for record-keeping
 */
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}

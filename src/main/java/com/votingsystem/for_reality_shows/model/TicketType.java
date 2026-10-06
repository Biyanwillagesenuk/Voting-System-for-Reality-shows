package com.votingsystem.for_reality_shows.model;

/**
 * What kind of support ticket this is.
 *
 * COMPLAINT / INQUIRY come from regular viewers.
 * HARASSMENT_REPORT / IMPERSONATION_REPORT come from contestants
 * (see the "Report abuse / impersonation" requirement gathered from
 * the contestant persona - Sahan Wijesinghe - in the requirements doc).
 */
public enum TicketType {
    COMPLAINT,
    INQUIRY,
    HARASSMENT_REPORT,
    IMPERSONATION_REPORT
}

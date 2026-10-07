package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.TicketResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Database queries for ticket replies. */
@Repository
public interface TicketResponseRepository extends JpaRepository<TicketResponse, Long> {
    /** Replies of one ticket, oldest first. */
    List<TicketResponse> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}

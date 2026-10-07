package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.SupportTicket;
import com.votingsystem.for_reality_shows.model.TicketStatus;
import com.votingsystem.for_reality_shows.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    Optional<SupportTicket> findByTicketRef(String ticketRef);

    List<SupportTicket> findByStatus(TicketStatus status);

    List<SupportTicket> findByType(TicketType type);

    List<SupportTicket> findBySubmittedByUserId(Long userId);

    List<SupportTicket> findByStatusAndType(TicketStatus status, TicketType type);

    @Query("SELECT COUNT(t) FROM SupportTicket t WHERE t.status = :status")
    long countByStatus(TicketStatus status);

    long countByTypeInAndStatusNot(List<TicketType> types, TicketStatus status);
}

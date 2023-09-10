package com.ticketapi.TicketManagementApp.TicketRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ticketapi.TicketManagementApp.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}

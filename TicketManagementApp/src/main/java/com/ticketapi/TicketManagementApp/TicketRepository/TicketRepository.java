package com.ticketapi.TicketManagementApp.TicketRepository;

import com.ticketapi.TicketManagementApp.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.sql.ResultSet;
import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket,Integer> {


    @Query(value = "SELECT * FROM tickets t WHERE id > 0 :jpql", nativeQuery = true)
    List<Ticket> searchTickets(@Param("jpql") String jpql);

    @Query(value = "SELECT * FROM tickets t where t.status=?1",nativeQuery = true)
   List<Ticket> getTicketByStatus(String status);
}

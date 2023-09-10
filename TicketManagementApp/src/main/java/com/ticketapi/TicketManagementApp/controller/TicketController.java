package com.ticketapi.TicketManagementApp.controller;

import com.ticketapi.TicketManagementApp.TicketService.TicketService;
import com.ticketapi.TicketManagementApp.constants.AppConstants;
import com.ticketapi.TicketManagementApp.entity.Ticket;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    @Autowired
    private final TicketService ticketService;
    private final Logger logger = LoggerFactory.getLogger(TicketController.class);

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllTickets() {
        logger.debug("Inside Controller GetAllTickets method");
        return ticketService.getAllTickets();
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getPaginatedTickets(@RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) int page, @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) int size, @RequestParam(value = "sortby", defaultValue = AppConstants.SORT_BY, required = false) String sortby, @RequestParam(value = "order", defaultValue = AppConstants.ORDER, required = false) String order

    ) {
        logger.debug("Inside Controller getPaginatedTickets method");
        return ticketService.getPaginatedTickets(page, size, sortby, order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Integer id) {
        logger.debug("Inside Controller getTicketById method");
        return ticketService.getTicketById(id);
    }

    @PostMapping()
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket) {
        logger.debug("Inside Controller createTicket method");
        return ticketService.createTicket(ticket);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody Ticket updatedTicket) {
        logger.debug("Inside Controller updateTicket method");
        return ticketService.updateTicket(id, updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Integer id) {
        logger.debug("Inside Controller deleteTicket method");
        return ticketService.deleteTicket(id);
    }
}

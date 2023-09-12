package com.ticketapi.TicketManagementApp.controller;

import com.ticketapi.TicketManagementApp.TicketService.TicketService;
import com.ticketapi.TicketManagementApp.constants.AppConstants;
import com.ticketapi.TicketManagementApp.entity.Ticket;
import com.ticketapi.TicketManagementApp.payloads.TicketDto;
import com.ticketapi.TicketManagementApp.payloads.TicketSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    private final Logger logger = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    private TicketService ticketService;

    @GetMapping("/getAllTickets")
    public ResponseEntity<?> getAllTickets() {
        logger.debug("Inside Controller GetAllTickets method");
        return ticketService.getAllTickets();
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getPaginatedTickets
            (@RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) int page,
             @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) int size,
             @RequestParam(value = "sortby", defaultValue = AppConstants.SORT_BY, required = false) String sortby,
             @RequestParam(value = "order", defaultValue = AppConstants.ORDER, required = false) String order
            ) {
        logger.debug("Inside Controller getPaginatedTickets method");
        return ticketService.getPaginatedTickets(page, size, sortby, order);
    }

    @GetMapping("/getTicketByID/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        logger.debug("Inside Controller getTicketById method");
        return ticketService.getTicketById(id);
    }

    @PostMapping("/addTicket")
    public ResponseEntity<?> createTicket(@RequestBody TicketDto ticketDto) {
        logger.debug("Inside Controller createTicket method");
        return ticketService.createTicket(ticketDto);
    }

    @PutMapping("/updateTicket/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody TicketDto updatedTicketDto) {
        logger.debug("Inside Controller updateTicket method");
        return ticketService.updateTicket(id, updatedTicketDto);
    }

    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable int id) {
        logger.debug("Inside Controller deleteTicket method");
        return ticketService.deleteTicket(id);
    }

    @PostMapping("/getTicket")
    public ResponseEntity<?> getTicket(@RequestBody TicketSearchRequest ticketSearchRequest) {
        return ticketService.getTicket(ticketSearchRequest);
    }
    @GetMapping("/getTicketByStatus/{status}")
    public ResponseEntity<?> getTicketByStatus(@PathVariable  String status) {
        return ticketService.getTicketByStatus(status);
    }
}

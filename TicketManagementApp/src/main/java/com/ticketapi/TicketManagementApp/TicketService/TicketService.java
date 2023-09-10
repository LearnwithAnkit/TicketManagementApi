package com.ticketapi.TicketManagementApp.TicketService;

import com.ticketapi.TicketManagementApp.exceptions.ResourceNotFoundException;
import com.ticketapi.TicketManagementApp.payloads.TicketDto;
import com.ticketapi.TicketManagementApp.payloads.TicketResponse;
import com.ticketapi.TicketManagementApp.util.ResponseHandler;
import com.ticketapi.TicketManagementApp.TicketRepository.TicketRepository;
import com.ticketapi.TicketManagementApp.entity.Ticket;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ResponseEntity<?> getAllTickets() {
        try {
            logger.debug("Inside Service getAllTickets method try block");
            List<Ticket> tickets = ticketRepository.findAll();
            List<TicketDto> ticketDtos = tickets.stream().map(this::TicketToTicketDto).collect(Collectors.toList());
            return ResponseHandler.generateResponse("Successfully fetched the tickets", HttpStatus.OK, ticketDtos);
        } catch (Exception e) {
            logger.debug("Inside Service getAllTickets method catch block");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK, null);
        }
    }


    public ResponseEntity<?> getTicketById(Integer id) {
        try {
            logger.debug("Inside Service getTicketById method try block");
            Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", id));
            return ResponseHandler.generateResponse("Successfully fetched the Ticket", HttpStatus.OK, TicketToTicketDto(ticket));
        } catch (ResourceNotFoundException e) {
            String message = e.getMessage();
            return ResponseHandler.generateResponse(message, HttpStatus.NOT_FOUND, "null");
        } catch (Exception ex) {
            logger.debug("Inside Service getTicketById method catch block");
            return ResponseHandler.generateResponse("Failed to fetch the ticket", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    public ResponseEntity<Object> createTicket(Ticket ticket) {
        try {
            logger.debug("Inside Service createTicket method try block");
            ticket.setLastModifiedDate(new Date());
            TicketDto ticketDto = TicketToTicketDto(ticketRepository.save(ticket));
            return ResponseHandler.generateResponse("Successfully Added Ticket", HttpStatus.CREATED, ticketDto);
        } catch (Exception e) {
            logger.debug("Inside Service createTicket method catch block");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    public ResponseEntity<Object> updateTicket(Integer id, Ticket updatedTicket) {

        try {
            logger.debug("Inside Service updateTicket method try block");
            updatedTicket.setLastModifiedDate(new Date());
            Ticket existingTicket = ticketRepository.findById(id).orElse(null);
            if (existingTicket == null)
                return ResponseHandler.generateResponse("Successfully Updated the Ticket", HttpStatus.OK, TicketToTicketDto(ticketRepository.save(updatedTicket)));
            else {
                existingTicket.setTicketCode(updatedTicket.getTicketCode());
                existingTicket.setStatus(updatedTicket.getStatus());
                existingTicket.setTitle(updatedTicket.getTitle());
                existingTicket.setClientId(updatedTicket.getClientId());
                existingTicket.setLastModifiedDate(updatedTicket.getLastModifiedDate());
                return ResponseHandler.generateResponse("Successfully Updated the Ticket", HttpStatus.OK, TicketToTicketDto(ticketRepository.save(existingTicket)));
            }
        } catch (Exception e) {
            logger.debug("Inside Service updateTicket method catch block");
            return ResponseHandler.generateResponse("Failed to Update the Ticket", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    public ResponseEntity<?> deleteTicket(Integer id) {
        try {
            Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", id));
            ticketRepository.delete(ticket);
            return ResponseHandler.generateResponse("Ticket Deleted Successfully", HttpStatus.OK, TicketToTicketDto(ticket));
        } catch (ResourceNotFoundException e) {
            String message = e.getMessage();
            return ResponseHandler.generateResponse(message, HttpStatus.NOT_FOUND, "null");
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to Delete the Ticket", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    public ResponseEntity<?> getPaginatedTickets(int page, int size, String sortby, String order) {
        try {
            Sort sort = "asc".equalsIgnoreCase(order) ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Ticket> pagetickets = ticketRepository.findAll(pageable);
            TicketResponse ticketResponse = this.getTicketResponse(pagetickets);
            return ResponseHandler.generateResponse("Successfully fetched the ticket", HttpStatus.OK, ticketResponse);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    private Ticket TicketDtoToTicket(TicketDto ticketdto) {
        return modelMapper.map(ticketdto, Ticket.class);
    }

    private TicketDto TicketToTicketDto(Ticket ticket) {
        return modelMapper.map(ticket, TicketDto.class);
    }

    public TicketResponse getTicketResponse(Page<Ticket> pagetickets) {
        List<Ticket> tickets = pagetickets.getContent();
        List<TicketDto> ticketDtos = tickets.stream().map(this::TicketToTicketDto).collect(Collectors.toList());
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketDtos(ticketDtos);
        ticketResponse.setPageNumber(pagetickets.getNumber());
        ticketResponse.setPageSize(pagetickets.getSize());
        ticketResponse.setLastPage(pagetickets.isLast());
        ticketResponse.setTotalElements(pagetickets.getTotalElements());
        ticketResponse.setTotalPages(pagetickets.getTotalPages());
        return ticketResponse;
    }


}

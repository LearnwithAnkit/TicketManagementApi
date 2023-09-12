package com.ticketapi.TicketManagementApp.TicketService;

import com.ticketapi.TicketManagementApp.TicketRepository.TicketRepository;
import com.ticketapi.TicketManagementApp.entity.Ticket;
import com.ticketapi.TicketManagementApp.exceptions.ResourceNotFoundException;
import com.ticketapi.TicketManagementApp.payloads.TicketDto;
import com.ticketapi.TicketManagementApp.payloads.TicketResponse;
import com.ticketapi.TicketManagementApp.payloads.TicketSearchRequest;
import com.ticketapi.TicketManagementApp.util.ResponseHandler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
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
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.OK);
        }
    }


    public ResponseEntity<?> getTicketById(int id) {
        try {
            logger.debug("Inside Service getTicketById method try block");
            Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", id));
            return ResponseHandler.generateResponse("Successfully fetched the Ticket", HttpStatus.OK, TicketToTicketDto(ticket));
        } catch (ResourceNotFoundException e) {
            String message = e.getMessage();
            return ResponseHandler.generateResponse(message, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.debug("Inside Service getTicketById method catch block");
            return ResponseHandler.generateResponse("Failed to fetch the ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createTicket(TicketDto ticketdto) {
        try {
            logger.debug("Inside Service createTicket method try block");
            Ticket ticket=TicketDtoToTicket(ticketdto);
            ticket.setLastModifiedDate(new Date());
            TicketDto ticketDto = TicketToTicketDto(ticketRepository.save(ticket));
            return ResponseHandler.generateResponse("Successfully Added Ticket", HttpStatus.CREATED, ticketDto);
        } catch (Exception e) {
            logger.debug("Inside Service createTicket method catch block");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateTicket(int id, TicketDto updatedTicketdto) {

        try {
            logger.debug("Inside Service updateTicket method try block");
            Ticket updatedTicket=TicketDtoToTicket(updatedTicketdto);
            updatedTicket.setLastModifiedDate(new Date());
            Ticket existingTicket = ticketRepository.findById(id).orElse(null);
            if (existingTicket == null)
                return ResponseHandler.generateResponse("Successfully Updated the Ticket", HttpStatus.OK, TicketToTicketDto(ticketRepository.save(updatedTicket)));
            else {
                existingTicket.setTicketCode(updatedTicketdto.getTicketCode());
                existingTicket.setStatus(updatedTicketdto.getStatus());
                existingTicket.setTitle(updatedTicketdto.getTitle());
                existingTicket.setClientId(updatedTicketdto.getClientId());
                existingTicket.setLastModifiedDate(new Date());
                return ResponseHandler.generateResponse("Successfully Updated the Ticket", HttpStatus.OK, TicketToTicketDto(ticketRepository.save(existingTicket)));
            }
        } catch (Exception e) {
            logger.debug("Inside Service updateTicket method catch block");
            return ResponseHandler.generateResponse("Failed to Update the Ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> deleteTicket(int id) {
        try {
            Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "UserID", id));
            ticketRepository.delete(ticket);
            return ResponseHandler.generateResponse("Ticket Deleted Successfully", HttpStatus.OK, TicketToTicketDto(ticket));
        } catch (ResourceNotFoundException e) {
            String message = e.getMessage();
            return ResponseHandler.generateResponse(message, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Failed to Delete the Ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getPaginatedTickets(int page, int size, String sortby, String order) {
        try {
            logger.debug("Inside Service getPaginatedTickets method try block");
            Sort sort = "asc".equalsIgnoreCase(order) ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Ticket> pagetickets = ticketRepository.findAll(pageable);
            TicketResponse ticketResponse = this.getTicketResponse(pagetickets);
            return ResponseHandler.generateResponse("Successfully fetched the ticket", HttpStatus.OK, ticketResponse);
        } catch (Exception e) {
            logger.debug("Inside Service getPaginatedTickets method try block");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public Ticket TicketDtoToTicket(TicketDto ticketdto) {
        return modelMapper.map(ticketdto, Ticket.class);
    }

    public TicketDto TicketToTicketDto(Ticket ticket) {
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


    public ResponseEntity<?> getTicket(TicketSearchRequest ticketSearchRequest) {
        try {
            StringBuilder jpqlBuilder = new StringBuilder();
            boolean flag = false;
            if (ticketSearchRequest.getId() != 0) {
                flag = true;
                jpqlBuilder.append(" AND t.id = ").append(ticketSearchRequest.getId());
            }

            if (ticketSearchRequest.getClientId() != 0) {
                flag = true;
                jpqlBuilder.append(" AND t.client_id = ").append(ticketSearchRequest.getClientId());
            }

            if (ticketSearchRequest.getTicketCode() != 0) {
                flag = true;
                jpqlBuilder.append(" AND t.ticket_code = ").append(ticketSearchRequest.getTicketCode());
            }

            if (ticketSearchRequest.getTitle() != null && !ticketSearchRequest.getTitle().isEmpty()) {
                flag = true;
                jpqlBuilder.append(" AND t.title = '").append(ticketSearchRequest.getTitle()).append("'");
            }

            if (ticketSearchRequest.getStatus() != null && !ticketSearchRequest.getStatus().isEmpty()) {
                flag = true;
                jpqlBuilder.append(" AND t.status = '").append(ticketSearchRequest.getStatus()).append("'");
            }
            if (!flag) return ResponseHandler.generateResponse("Enter valid request details", HttpStatus.BAD_REQUEST);
            String jpql = jpqlBuilder.toString();
            System.out.println(jpql);
            List<Ticket> tickets = ticketRepository.searchTickets(jpql);
            if (tickets == null || tickets.isEmpty())
                return ResponseHandler.generateResponse("Ticket Not found", HttpStatus.NOT_FOUND);
            else
                return ResponseHandler.generateResponse("Ticket fetched successfully", HttpStatus.OK, tickets);
        }
        catch (Exception e)
        {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getTicketByStatus(String status) {
        try{
            List<Ticket> tickets=ticketRepository.getTicketByStatus(status);
            if(tickets.isEmpty()) return ResponseHandler.generateResponse("No ticket Found",HttpStatus.OK);
            List<TicketDto> ticketDtos=tickets.stream().map(this::TicketToTicketDto).collect(Collectors.toList());
            return ResponseHandler.generateResponse("Successfully fetched the tickets",HttpStatus.OK,ticketDtos);
        }
        catch (Exception e)
        {
            return ResponseHandler.generateResponse("Failed to fetch the tickets",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

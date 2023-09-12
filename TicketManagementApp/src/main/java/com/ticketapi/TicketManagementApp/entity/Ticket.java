package com.ticketapi.TicketManagementApp.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "ticket_code", unique = true)
    private int ticketCode;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "last_modified_date")
    @LastModifiedBy
    private Date lastModifiedDate;

    @Column(name = "status", length = 20)
    private String status;
}

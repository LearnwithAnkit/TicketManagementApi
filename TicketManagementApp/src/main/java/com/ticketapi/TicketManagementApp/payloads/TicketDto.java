package com.ticketapi.TicketManagementApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketDto {
    private int id;
    private int clientId;
    private int ticketCode;
    private String title;
    private Date lastModifiedDate;
    private String status;
}

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
    private Integer id;
    private Integer clientId;
    private Integer ticketCode;
    private String title;
    private Date lastModifiedDate;
    private String status;
}

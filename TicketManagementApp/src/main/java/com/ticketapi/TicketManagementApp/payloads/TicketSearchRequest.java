package com.ticketapi.TicketManagementApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchRequest {
    private int id;
    private int clientId;
    private int ticketCode;
    private String title;
    private String status;
}

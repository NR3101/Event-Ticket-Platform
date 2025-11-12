package com.neeraj.ticketsapp.services;

import com.neeraj.ticketsapp.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}

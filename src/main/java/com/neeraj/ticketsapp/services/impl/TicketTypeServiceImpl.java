package com.neeraj.ticketsapp.services.impl;

import com.neeraj.ticketsapp.domain.entities.Ticket;
import com.neeraj.ticketsapp.domain.entities.TicketStatus;
import com.neeraj.ticketsapp.domain.entities.TicketType;
import com.neeraj.ticketsapp.domain.entities.User;
import com.neeraj.ticketsapp.exceptions.TicketTypeNotFoundException;
import com.neeraj.ticketsapp.exceptions.TicketsSoldOutException;
import com.neeraj.ticketsapp.exceptions.UserNotFoundException;
import com.neeraj.ticketsapp.repositories.TicketRepository;
import com.neeraj.ticketsapp.repositories.TicketTypeRepository;
import com.neeraj.ticketsapp.repositories.UserRepository;
import com.neeraj.ticketsapp.services.QRCodeService;
import com.neeraj.ticketsapp.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QRCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException("TicketType not found with id: " + ticketTypeId));

        int ticketsPurchased = ticketRepository.countByTicketTypeId(ticketTypeId);
        if (ticketsPurchased > ticketType.getTotalAvailable()) {
            throw new TicketsSoldOutException("No tickets available for TicketType id: " + ticketTypeId);
        }

        Ticket ticket = Ticket.builder()
                .status(TicketStatus.PURCHASED)
                .ticketType(ticketType)
                .purchaser(user)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQRCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}

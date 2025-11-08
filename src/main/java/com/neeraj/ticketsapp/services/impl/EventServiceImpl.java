package com.neeraj.ticketsapp.services.impl;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.entities.Event;
import com.neeraj.ticketsapp.domain.entities.TicketType;
import com.neeraj.ticketsapp.domain.entities.User;
import com.neeraj.ticketsapp.exceptions.UserNotFoundException;
import com.neeraj.ticketsapp.repositories.EventRepository;
import com.neeraj.ticketsapp.repositories.UserRepository;
import com.neeraj.ticketsapp.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest eventRequest) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException("Organizer not found with id: " + organizerId));

        Event eventToCreate = Event
                .builder()
                .name(eventRequest.getName())
                .startTime(eventRequest.getStartTime())
                .endTime(eventRequest.getEndTime())
                .venue(eventRequest.getVenue())
                .salesStartTime(eventRequest.getSalesStartTime())
                .salesEndTime(eventRequest.getSalesEndTime())
                .status(eventRequest.getStatus())
                .organizer(organizer)
                .build();

        List<TicketType> ticketTypesToCreate = eventRequest.getTicketTypes().stream().map(ticketType -> TicketType
                .builder()
                .name(ticketType.getName())
                .price(ticketType.getPrice())
                .description(ticketType.getDescription())
                .totalAvailable(ticketType.getTotalAvailable())
                .event(eventToCreate)
                .build()).toList();

        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }
}

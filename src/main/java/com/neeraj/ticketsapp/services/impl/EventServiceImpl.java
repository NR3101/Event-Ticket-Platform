package com.neeraj.ticketsapp.services.impl;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.UpdateEventRequest;
import com.neeraj.ticketsapp.domain.UpdateTicketTypeRequest;
import com.neeraj.ticketsapp.domain.entities.Event;
import com.neeraj.ticketsapp.domain.entities.TicketType;
import com.neeraj.ticketsapp.domain.entities.User;
import com.neeraj.ticketsapp.exceptions.EventNotFoundException;
import com.neeraj.ticketsapp.exceptions.EventUpdateException;
import com.neeraj.ticketsapp.exceptions.TicketTypeNotFoundException;
import com.neeraj.ticketsapp.exceptions.UserNotFoundException;
import com.neeraj.ticketsapp.repositories.EventRepository;
import com.neeraj.ticketsapp.repositories.UserRepository;
import com.neeraj.ticketsapp.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
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

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
    }

    @Override
    @Transactional // For making sure all DB operations are in a single transaction
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest eventRequest) {
        if (eventRequest.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if (!eventRequest.getId().equals(eventId)) {
            throw new EventUpdateException("Provided IDs do not match");
        }

        // Fetch existing event
        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(() -> new EventNotFoundException("Event not found or does not belong to organizer"));

        // Update event details
        existingEvent.setName(eventRequest.getName());
        existingEvent.setStartTime(eventRequest.getStartTime());
        existingEvent.setEndTime(eventRequest.getEndTime());
        existingEvent.setVenue(eventRequest.getVenue());
        existingEvent.setSalesStartTime(eventRequest.getSalesStartTime());
        existingEvent.setSalesEndTime(eventRequest.getSalesEndTime());
        existingEvent.setStatus(eventRequest.getStatus());

        // Get IDs of ticket types from the request that are not null
        Set<UUID> requestTicketTypeIds = eventRequest.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Remove ticket types that are not present in the request from the existing event
        existingEvent.getTicketTypes().removeIf(existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId()));

        // Index existing ticket types by ID for easy lookup
        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        // Update existing ticket types and add new ones
        for (UpdateTicketTypeRequest ticketTypeRequest : eventRequest.getTicketTypes()) {
            // If ID is null, it's a new ticket type
            if (ticketTypeRequest.getId() == null) {
                TicketType newTicketType = TicketType
                        .builder()
                        .name(ticketTypeRequest.getName())
                        .price(ticketTypeRequest.getPrice())
                        .description(ticketTypeRequest.getDescription())
                        .totalAvailable(ticketTypeRequest.getTotalAvailable())
                        .event(existingEvent)
                        .build();
                existingEvent.getTicketTypes().add(newTicketType);
            }
            // else if ID exists, update the existing ticket type
            else if (existingTicketTypesIndex.containsKey(ticketTypeRequest.getId())) {
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketTypeRequest.getId());
                existingTicketType.setName(ticketTypeRequest.getName());
                existingTicketType.setPrice(ticketTypeRequest.getPrice());
                existingTicketType.setDescription(ticketTypeRequest.getDescription());
                existingTicketType.setTotalAvailable(ticketTypeRequest.getTotalAvailable());
            }
            // else throw exception if ticket type ID not found
            else {
                throw new TicketTypeNotFoundException("Ticket Type not found with id: " + ticketTypeRequest.getId());
            }
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(() -> new EventNotFoundException("Event not found or does not belong to organizer"));

        eventRepository.delete(existingEvent);
    }
}

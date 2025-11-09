package com.neeraj.ticketsapp.services;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.UpdateEventRequest;
import com.neeraj.ticketsapp.domain.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest eventRequest);

    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId);

    Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest eventRequest);

    void deleteEventForOrganizer(UUID organizerId, UUID eventId);

    Page<Event> listPublishedEvents(Pageable pageable);

    Page<Event> searchPublishedEvents(String query, Pageable pageable);

    Optional<Event> getPublishedEventDetails(UUID eventId);
}

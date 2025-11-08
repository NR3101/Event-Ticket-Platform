package com.neeraj.ticketsapp.services;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest eventRequest);
}

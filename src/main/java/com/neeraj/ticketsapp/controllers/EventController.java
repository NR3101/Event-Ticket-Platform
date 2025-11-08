package com.neeraj.ticketsapp.controllers;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.dtos.CreateEventRequestDTO;
import com.neeraj.ticketsapp.domain.dtos.CreateEventResponseDTO;
import com.neeraj.ticketsapp.domain.entities.Event;
import com.neeraj.ticketsapp.mappers.EventMapper;
import com.neeraj.ticketsapp.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(@AuthenticationPrincipal Jwt jwt,
                                                              @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDTO);
        UUID userId = UUID.fromString(jwt.getSubject());

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDTO responseDTO = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}

package com.neeraj.ticketsapp.controllers;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.UpdateEventRequest;
import com.neeraj.ticketsapp.domain.dtos.*;
import com.neeraj.ticketsapp.domain.entities.Event;
import com.neeraj.ticketsapp.mappers.EventMapper;
import com.neeraj.ticketsapp.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.neeraj.ticketsapp.util.JwtUtil.parseUserIdFromJwt;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO
    ) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDTO);
        UUID userId = parseUserIdFromJwt(jwt);

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDTO responseDTO = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDTO>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        UUID userId = parseUserIdFromJwt(jwt);
        Page<Event> eventsPage = eventService.listEventsForOrganizer(userId, pageable);
        Page<ListEventResponseDTO> responseDTOPage = eventsPage.map(eventMapper::toListEventResponseDto);
        return ResponseEntity.ok(responseDTOPage);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDTO> getEventDetails(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserIdFromJwt(jwt);
        return eventService.getEventForOrganizer(userId, eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<UpdateEventResponseDTO> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDTO updateEventRequestDTO
    ) {
        UUID userId = parseUserIdFromJwt(jwt);
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDTO);

        Event updatedEvent = eventService.updateEventForOrganizer(userId, eventId, updateEventRequest);
        UpdateEventResponseDTO responseDTO = eventMapper.toUpdateEventResponseDto(updatedEvent);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserIdFromJwt(jwt);
        eventService.deleteEventForOrganizer(userId, eventId);
        return ResponseEntity.noContent().build();
    }

}

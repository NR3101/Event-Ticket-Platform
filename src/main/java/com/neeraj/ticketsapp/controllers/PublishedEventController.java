package com.neeraj.ticketsapp.controllers;

import com.neeraj.ticketsapp.domain.dtos.GetPublishedEventDetailsResponseDTO;
import com.neeraj.ticketsapp.domain.dtos.ListPublishedEventResponseDTO;
import com.neeraj.ticketsapp.domain.entities.Event;
import com.neeraj.ticketsapp.exceptions.EventNotFoundException;
import com.neeraj.ticketsapp.mappers.EventMapper;
import com.neeraj.ticketsapp.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDTO>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable) {
        Page<Event> event;
        if (q != null && !q.trim().isEmpty()) {
            event = eventService.searchPublishedEvents(q, pageable);
        } else {
            event = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(event.map(eventMapper::toListPublishedEventResponseDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDTO> getPublishedEventDetails(@PathVariable UUID eventId) {
        return eventService.getPublishedEventDetails(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

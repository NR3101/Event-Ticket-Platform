package com.neeraj.ticketsapp.domain;

import com.neeraj.ticketsapp.domain.entities.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventRequest {
    private String name;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime salesStartTime;
    private LocalDateTime salesEndTime;
    private EventStatus status;
    private List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();
}

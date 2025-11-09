package com.neeraj.ticketsapp.domain.dtos;

import com.neeraj.ticketsapp.domain.entities.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPublishedEventDetailsResponseDTO {
    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
    private List<GetPublishedEventDetailsTicketTypeResponseDTO> ticketTypes = new ArrayList<>();
}

package com.neeraj.ticketsapp.domain.dtos;

import com.neeraj.ticketsapp.domain.entities.EventStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEventRequestDTO {
    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Venue is required")
    private String venue;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime salesStartTime;

    private LocalDateTime salesEndTime;

    @NotNull(message = "Event status is required")
    private EventStatus status;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDTO> ticketTypes;
}

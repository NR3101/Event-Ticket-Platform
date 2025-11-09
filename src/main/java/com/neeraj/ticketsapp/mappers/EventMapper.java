package com.neeraj.ticketsapp.mappers;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.CreateTicketTypeRequest;
import com.neeraj.ticketsapp.domain.UpdateEventRequest;
import com.neeraj.ticketsapp.domain.UpdateTicketTypeRequest;
import com.neeraj.ticketsapp.domain.dtos.*;
import com.neeraj.ticketsapp.domain.entities.Event;
import com.neeraj.ticketsapp.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDTO dto);

    CreateEventRequest fromDto(CreateEventRequestDTO dto);

    CreateEventResponseDTO toDto(Event event);

    ListEventTicketTypeResponseDTO toDto(TicketType ticketType);

    ListEventResponseDTO toListEventResponseDto(Event event);

    GetEventDetailsTicketTypeResponseDTO toGetEventDetailsTicketTypeDto(TicketType ticketType);

    GetEventDetailsResponseDTO toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDTO dto);

    UpdateEventRequest fromDto(UpdateEventRequestDTO dto);

    UpdateTicketTypeResponseDTO toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDTO toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDTO toListPublishedEventResponseDto(Event event);

    GetPublishedEventDetailsResponseDTO toGetPublishedEventDetailsResponseDto(Event event);

    GetPublishedEventDetailsTicketTypeResponseDTO toGetPublishedEventDetailsTicketTypeDto(TicketType ticketType);
}

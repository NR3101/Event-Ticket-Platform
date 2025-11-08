package com.neeraj.ticketsapp.mappers;

import com.neeraj.ticketsapp.domain.CreateEventRequest;
import com.neeraj.ticketsapp.domain.CreateTicketTypeRequest;
import com.neeraj.ticketsapp.domain.dtos.CreateEventRequestDTO;
import com.neeraj.ticketsapp.domain.dtos.CreateEventResponseDTO;
import com.neeraj.ticketsapp.domain.dtos.CreateTicketTypeRequestDTO;
import com.neeraj.ticketsapp.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDTO dto);

    CreateEventRequest fromDto(CreateEventRequestDTO dto);

    CreateEventResponseDTO toDto(Event event);
}

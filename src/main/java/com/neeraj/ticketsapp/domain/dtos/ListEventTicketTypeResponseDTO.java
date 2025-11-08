package com.neeraj.ticketsapp.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListEventTicketTypeResponseDTO {
    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;
}

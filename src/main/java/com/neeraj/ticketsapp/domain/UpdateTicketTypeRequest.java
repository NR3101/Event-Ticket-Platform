package com.neeraj.ticketsapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTicketTypeRequest {
    private UUID id;
    private String name;
    private double price;
    private String description;
    private Integer totalAvailable;
}

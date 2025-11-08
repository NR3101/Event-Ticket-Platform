package com.neeraj.ticketsapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketTypeRequest {
    private String name;
    private double price;
    private String description;
    private Integer totalAvailable;
}

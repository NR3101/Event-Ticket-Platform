package com.neeraj.ticketsapp.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketType extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    private String description;

    private Integer totalAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
}

package com.neeraj.ticketsapp.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_validations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketValidation extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketValidationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_method", nullable = false)
    private TicketValidationMethod validationMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}

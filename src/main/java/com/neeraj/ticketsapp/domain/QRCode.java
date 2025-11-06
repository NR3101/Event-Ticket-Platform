package com.neeraj.ticketsapp.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "qr_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QRCode extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QRCodeStatus status;

    @Column(nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}

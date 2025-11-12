package com.neeraj.ticketsapp.services;

import com.neeraj.ticketsapp.domain.entities.QRCode;
import com.neeraj.ticketsapp.domain.entities.Ticket;

public interface QRCodeService {
    QRCode generateQRCode(Ticket ticket);
}

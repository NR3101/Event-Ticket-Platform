package com.neeraj.ticketsapp.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.neeraj.ticketsapp.domain.entities.QRCode;
import com.neeraj.ticketsapp.domain.entities.QRCodeStatus;
import com.neeraj.ticketsapp.domain.entities.Ticket;
import com.neeraj.ticketsapp.exceptions.QRCodeGenerationException;
import com.neeraj.ticketsapp.repositories.QRCodeRepository;
import com.neeraj.ticketsapp.services.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeRepository qrCodeRepository;
    private final QRCodeWriter qrCodeWriter;

    @Override
    public QRCode generateQRCode(Ticket ticket) {
        try {
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uniqueId);

            QRCode qrCode = QRCode.builder()
                    .id(uniqueId)
                    .status(QRCodeStatus.ACTIVE)
                    .value(qrCodeImage)
                    .ticket(ticket)
                    .build();

            return qrCodeRepository.saveAndFlush(qrCode);
        } catch (WriterException | IOException e) {
            throw new QRCodeGenerationException("Failed to generate QR Code", e);
        }
    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}

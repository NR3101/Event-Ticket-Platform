package com.neeraj.ticketsapp.controllers;

import com.neeraj.ticketsapp.domain.dtos.ErrorDTO;
import com.neeraj.ticketsapp.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketsSoldOutException.class)
    public ResponseEntity<ErrorDTO> handleTicketsSoldOutException(TicketsSoldOutException ex) {
        log.error("A TicketsSoldOutException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Tickets are sold out.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QRCodeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleQRCodeNotFoundException(QRCodeNotFoundException ex) {
        log.error("A QRCodeNotFoundException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("QR code not found.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(QRCodeGenerationException.class)
    public ResponseEntity<ErrorDTO> handleQRCodeGenerationException(QRCodeGenerationException ex) {
        log.error("A QRCodeGenerationException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Failed to generate QR code.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorDTO> handleEventUpdateException(EventUpdateException ex) {
        log.error("An EventUpdateException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Failed to update event.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex) {
        log.error("A TicketTypeNotFoundException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Ticket type not found.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEventNotFoundException(EventNotFoundException ex) {
        log.error("A EventNotFoundException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Event not found.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("A UserNotFoundException occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("User not found.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("A MethodArgumentNotValidException occurred: ", ex);

        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Validation error occurred.");
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessages)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("A validation error occurred: ", ex);

        String errorMessages = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation ->
                        violation.getPropertyPath() + ": " + violation.getMessage())
                .orElse("Constraint violation error occurred.");
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessages)
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        log.error("An unexpected error occurred: ", ex);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("An unexpected error occurred. Please try again later.")
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

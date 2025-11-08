package com.neeraj.ticketsapp.controllers;

import com.neeraj.ticketsapp.domain.dtos.ErrorDTO;
import com.neeraj.ticketsapp.exceptions.UserNotFoundException;
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(Exception ex) {
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

package com.rahul.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(Exception exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(500)
            .message(exception.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

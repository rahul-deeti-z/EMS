package com.rahul.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EmployeeExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException exception) {
    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .message(exception.getMessage())
            .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}

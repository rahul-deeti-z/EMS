package com.rahul.ems.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class EntityExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex) {
    List<String> errors = ex.getConstraintViolations()
            .stream()
            .map(violation -> {
              String entity = violation.getRootBeanClass().getSimpleName(); // Get entity name
              String field = violation.getPropertyPath().toString(); // Get field name
              String message = violation.getMessage(); // Get validation message
              return entity + "." + field + ": " + message;
            })
            .collect(Collectors.toList());

    ErrorResponse errorResponse =
        ErrorResponse.builder()
            .status(400)
            .errors(errors)
            .message("Entity validation failed")
            .timestamp(LocalDateTime.now())
            .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}

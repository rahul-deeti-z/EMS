package com.rahul.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler extends RuntimeException {
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}

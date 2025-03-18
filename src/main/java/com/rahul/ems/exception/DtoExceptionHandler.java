package com.rahul.ems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DtoExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleDtoValidationException(MethodArgumentNotValidException ex) {
        // Extract DTO class name
        String dtoName = ex.getBindingResult().getTarget() != null
                ? ex.getBindingResult().getTarget().getClass().getSimpleName()
                : "UnknownDTO";

        // Extract field errors
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> formatErrorMessage(dtoName, fieldError))
                .collect(Collectors.toList());

        // Build error response
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("DTO validation failed")
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String formatErrorMessage(String dtoName, FieldError fieldError) {
        return dtoName + "." + fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}

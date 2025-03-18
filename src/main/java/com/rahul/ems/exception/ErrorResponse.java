package com.rahul.ems.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Avoids null fields in JSON
public class ErrorResponse {
  private final int status;
  private final String message;
  private final LocalDateTime timestamp;
  private final List<String> errors;
}

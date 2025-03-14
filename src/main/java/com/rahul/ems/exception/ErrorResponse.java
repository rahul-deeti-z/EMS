package com.rahul.ems.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {
  private int status;
  private String message;
  private LocalDateTime timestamp;

  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }
}

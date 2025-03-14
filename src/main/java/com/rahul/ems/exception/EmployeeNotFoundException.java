package com.rahul.ems.exception;

public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(String message) {
    super(message);
  }
}

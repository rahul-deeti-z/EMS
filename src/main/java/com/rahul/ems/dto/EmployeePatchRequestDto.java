package com.rahul.ems.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// @JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON request
public class EmployeePatchRequestDto {
  @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
  private String firstName;

  @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
  private String lastName;

  @Email(message = "Invalid email format")
  private String email;

  @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
  private String phone;
}

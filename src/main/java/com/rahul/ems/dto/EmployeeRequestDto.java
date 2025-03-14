package com.rahul.ems.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
  @NotNull(message = "First name cannot be empty")
  @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
  private String firstName;

  @NotNull(message = "Last name cannot be empty")
  @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
  private String lastName;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Phone number is required")
  private String phone;
}

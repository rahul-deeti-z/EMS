package com.rahul.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
}

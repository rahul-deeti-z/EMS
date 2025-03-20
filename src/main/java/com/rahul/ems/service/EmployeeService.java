package com.rahul.ems.service;

import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
  EmployeeResponseDto getEmployeeById(Long id);

  List<EmployeeResponseDto> getAllEmployees();

  EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

  EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto);

  EmployeeResponseDto partiallyUpdateEmployee(Long id, EmployeePatchRequestDto patchRequestDto);

  void deleteEmployee(Long id);
}

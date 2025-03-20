package com.rahul.ems.controller;

import com.rahul.ems.api.EmployeeApi;
import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;
import com.rahul.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController implements EmployeeApi {
  private final EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
    EmployeeResponseDto responseDto = employeeService.getEmployeeById(id);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @GetMapping("/employees")
  public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
    List<EmployeeResponseDto> responseDtos = employeeService.getAllEmployees();
    return new ResponseEntity<>(responseDtos, HttpStatus.OK);
  }

  @PostMapping("/employees")
  public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto) {
    EmployeeResponseDto responseDto = employeeService.createEmployee(requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Long empId) {
    employeeService.deleteEmployee(empId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/employees/{id}")
  public ResponseEntity<EmployeeResponseDto> updateEmployee(
      @PathVariable Long id, @Valid @RequestBody EmployeeRequestDto requestDto) {
    EmployeeResponseDto responseDto = employeeService.updateEmployee(id, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PatchMapping("/employees/{id}")
  public ResponseEntity<EmployeeResponseDto> partiallyUpdateEmployee(
      @PathVariable Long id, @Valid @RequestBody EmployeePatchRequestDto patchRequestDto) {
    EmployeeResponseDto responseDto = employeeService.partiallyUpdateEmployee(id, patchRequestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}

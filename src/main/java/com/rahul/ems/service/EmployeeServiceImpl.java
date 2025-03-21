package com.rahul.ems.service;

import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;
import com.rahul.ems.entity.Employee;
import com.rahul.ems.exception.EmployeeNotFoundException;
import com.rahul.ems.mapper.EmployeeMapper;
import com.rahul.ems.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeMapper employeeMapper;
  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository) {
    this.employeeMapper = employeeMapper;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public EmployeeResponseDto getEmployeeById(Long id) {
    log.info("Fetching employee details for ID: {}", id);
    long startTime = System.currentTimeMillis();
    Employee employee = getEmployeeFromDb(id);
    long endTime = System.currentTimeMillis();
    log.info("Successfully fetched employee ID={} in {}ms", id, (endTime - startTime));
    return employeeMapper.toDto(employee);
  }

  @Override
  public List<EmployeeResponseDto> getAllEmployees() {
    List<Employee> employees = employeeRepository.findAll();
    return employeeMapper.toDtoList(employees);
  }

  @Override
  public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
    Employee newEmployee = employeeMapper.toEntity(requestDto);

    Employee dbEmployee = employeeRepository.save(newEmployee);

    return employeeMapper.toDto(dbEmployee);
  }

  @Override
  public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
    Employee employee = getEmployeeFromDb(id);
    employeeMapper.updateEmployeeFromDto(requestDto, employee);
    Employee updatedEmployee = employeeRepository.save(employee);

    return employeeMapper.toDto(updatedEmployee);
  }

  @Override
  public EmployeeResponseDto partiallyUpdateEmployee(
      Long id, EmployeePatchRequestDto patchRequestDto) {
    Employee employee = getEmployeeFromDb(id);
    employeeMapper.partiallyUpdateEmployeeFromDto(patchRequestDto, employee);
    Employee updatedEmployee = employeeRepository.save(employee);
    return employeeMapper.toDto(updatedEmployee);
  }

  @Override
  public void deleteEmployee(Long id) {
    Employee employee = getEmployeeFromDb(id);

    employeeRepository.delete(employee);
  }

  private Employee getEmployeeFromDb(Long id) {
    return employeeRepository
        .findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
  }
}

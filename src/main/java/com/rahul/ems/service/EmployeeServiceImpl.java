package com.rahul.ems.service;

import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;
import com.rahul.ems.entity.Employee;
import com.rahul.ems.exception.EmployeeNotFoundException;
import com.rahul.ems.mapper.EmployeeMapper;
import com.rahul.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
  private final EmployeeMapper employeeMapper;
  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository) {
    this.employeeMapper = employeeMapper;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public EmployeeResponseDto getEmployeeById(Long id) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(
                () -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
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
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(
                () -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    employeeMapper.updateEmployeeFromDto(requestDto, employee);
    Employee updatedEmployee = employeeRepository.save(employee);

    return employeeMapper.toDto(updatedEmployee);
  }

  @Override
  public EmployeeResponseDto partiallyUpdateEmployee(
      Long id, EmployeePatchRequestDto patchRequestDto) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(
                () -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    employeeMapper.partiallyUpdateEmployeeFromDto(patchRequestDto, employee);
    Employee updatedEmployee = employeeRepository.save(employee);
    return employeeMapper.toDto(updatedEmployee);
  }

  @Override
  public void deleteEmployee(Long id) {
    Employee employee =
        employeeRepository
            .findById(id)
            .orElseThrow(
                () -> new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    employeeRepository.delete(employee);
  }
}

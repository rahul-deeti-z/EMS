package com.rahul.ems.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;
import com.rahul.ems.entity.Employee;
import com.rahul.ems.exception.EmployeeNotFoundException;
import com.rahul.ems.mapper.EmployeeMapper;
import com.rahul.ems.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
  @Mock private EmployeeRepository employeeRepository;

  @Spy
  private final EmployeeMapper employeeMapper =
      Mappers.getMapper(EmployeeMapper.class); // Get MapStruct instance

  @InjectMocks private EmployeeServiceImpl employeeService;

  @Captor private ArgumentCaptor<Employee> employeeCaptor;

  private Employee mockEmployee;
  private EmployeeRequestDto mockEmployeeRequestDto;
  private EmployeePatchRequestDto mockEmployePatchRequestDto;

  @BeforeEach
  void setUp() {
    mockEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com", "1234567890");
    mockEmployeeRequestDto =
        new EmployeeRequestDto("Sheetal", "Verma", "sheetal@example.com", "1233211231");
    mockEmployePatchRequestDto = new EmployeePatchRequestDto();
    mockEmployePatchRequestDto.setFirstName("New First Name");
    mockEmployePatchRequestDto.setLastName("New Last Name");
  }

  @Test
  void shouldReturnEmployeeWhenIdIsValid() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));

    EmployeeResponseDto responseDto = employeeService.getEmployeeById(1L);

    assertNotNull(responseDto);
    assertEquals(mockEmployee.getId(), responseDto.getId());
    assertEquals(mockEmployee.getFirstName(), responseDto.getFirstName());
    assertEquals(mockEmployee.getLastName(), responseDto.getLastName());
    assertEquals(mockEmployee.getEmail(), responseDto.getEmail());
    assertEquals(mockEmployee.getPhone(), responseDto.getPhone());

    verify(employeeRepository, times(1)).findById(1L);
  }

  @Test
  void shouldThrowExceptionWhenEmployeeNotFound() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    verify(employeeRepository, times(1)).findById(1L);
  }

  @Test
  void shouldGetAllEmployees() {
    when(employeeRepository.findAll()).thenReturn(List.of(mockEmployee, new Employee()));
    List<EmployeeResponseDto> responseDtos = employeeService.getAllEmployees();
    assertEquals(2, responseDtos.size());
  }

  @Test
  void shouldSuccessfullyCreateEmployee() {
    Employee emp = new Employee(2L, "Sheetal", "Verma", "sheetal@example.com", "1233211231");

    when(employeeRepository.save(any(Employee.class))).thenReturn(emp);

    EmployeeResponseDto responseDto = employeeService.createEmployee(mockEmployeeRequestDto);

    // Assert that responseDto values should have same values as requestDto values
    verify(employeeRepository, times(1)).save(any(Employee.class));

    assertEquals(emp.getFirstName(), responseDto.getFirstName());
    assertEquals(emp.getLastName(), responseDto.getLastName());
    assertEquals(emp.getEmail(), responseDto.getEmail());
    assertEquals(emp.getPhone(), responseDto.getPhone());
  }

  @Test
  void shouldUpdateEmployeeWhenIdIsValid() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
    when(employeeRepository.save(any(Employee.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    EmployeeResponseDto responseDto = employeeService.updateEmployee(1L, mockEmployeeRequestDto);

    // Capture the employee object passed to save()
    verify(employeeRepository).save(employeeCaptor.capture());
    Employee capturedEmployee = employeeCaptor.getValue();

    // Assert that the values passed to "save" method should contain the same values as request dto
    assertEquals(mockEmployee.getId(), capturedEmployee.getId());
    assertEquals(mockEmployeeRequestDto.getFirstName(), capturedEmployee.getFirstName());
    assertEquals(mockEmployeeRequestDto.getLastName(), capturedEmployee.getLastName());
    assertEquals(mockEmployeeRequestDto.getEmail(), capturedEmployee.getEmail());
    assertEquals(mockEmployeeRequestDto.getPhone(), capturedEmployee.getPhone());

    // Assert that the responseDto contains updated values
    assertEquals(capturedEmployee.getId(), responseDto.getId());
    assertEquals(mockEmployeeRequestDto.getFirstName(), responseDto.getFirstName());
    assertEquals(mockEmployeeRequestDto.getLastName(), responseDto.getLastName());
    assertEquals(mockEmployeeRequestDto.getEmail(), responseDto.getEmail());
    assertEquals(mockEmployeeRequestDto.getPhone(), responseDto.getPhone());
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentEmployee() {
    // Arrange: Simulate employee not found
    Long employeeId = 1L;
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

    // Act & Assert: Verify that the exception is thrown
    assertThrows(
        EmployeeNotFoundException.class,
        () -> employeeService.updateEmployee(employeeId, mockEmployeeRequestDto));

    // Verify that save was never called
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  @Test
  void shouldSuccessfullyDoPartialUpdateWhenIdIsValid() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
    when(employeeRepository.save(any(Employee.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    EmployeeResponseDto responseDto =
        employeeService.partiallyUpdateEmployee(1L, mockEmployePatchRequestDto);

    // Capture the employee object passed to save()
    verify(employeeRepository).save(employeeCaptor.capture());
    Employee capturedEmployee = employeeCaptor.getValue();

    // Assert that the values passed to "save" method should contain the same values as request dto
    assertEquals(mockEmployee.getId(), capturedEmployee.getId());
    assertEquals(mockEmployePatchRequestDto.getFirstName(), capturedEmployee.getFirstName());
    assertEquals(mockEmployePatchRequestDto.getLastName(), capturedEmployee.getLastName());
    assertEquals(mockEmployee.getEmail(), capturedEmployee.getEmail());
    assertEquals(mockEmployee.getPhone(), capturedEmployee.getPhone());

    // Assert that the responseDto contains updated values
    assertEquals(mockEmployee.getId(), responseDto.getId());
    assertEquals(mockEmployePatchRequestDto.getFirstName(), responseDto.getFirstName());
    assertEquals(mockEmployePatchRequestDto.getLastName(), responseDto.getLastName());
    assertEquals(mockEmployee.getEmail(), responseDto.getEmail());
    assertEquals(mockEmployee.getPhone(), responseDto.getPhone());
  }

  @Test
  void shouldThrowExceptionWhenPartiallyUpdatingNonExistentEmployee() {
    // Arrange: Simulate employee not found
    Long employeeId = 1L;
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

    // Act & Assert: Verify that the exception is thrown
    assertThrows(
        EmployeeNotFoundException.class,
        () -> employeeService.partiallyUpdateEmployee(employeeId, mockEmployePatchRequestDto));

    // Verify that save was never called
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  @Test
  void shouldSuccessfullyDeleteEmployeeWhenIdIsValid() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));

    employeeService.deleteEmployee(1L);

    verify(employeeRepository, times(1)).delete(mockEmployee);
  }

  @Test
  void shouldThrowExceptionWhenDeletingNonExistentEmployee() {
    when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(1L));
    verify(employeeRepository, never()).delete(any(Employee.class));
  }
}

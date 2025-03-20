package com.rahul.ems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;
import com.rahul.ems.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
// @AutoConfigureMockMvc(addFilters = false) // this is to circumvent spring security
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  @MockitoBean private EmployeeService employeeService;

  private EmployeeRequestDto mockRequestDto;
  private EmployeeResponseDto mockResponseDto;
  private EmployeePatchRequestDto mockPatchRequestDto;

  @BeforeEach
  void setup() {
    mockRequestDto = new EmployeeRequestDto("John", "Doe", "john@example.com", "1234567890");
    mockResponseDto = new EmployeeResponseDto(1L, "John", "Doe", "john@example.com", "1234567890");
    mockPatchRequestDto = new EmployeePatchRequestDto();
    mockPatchRequestDto.setFirstName("New First Name");
    mockPatchRequestDto.setLastName("New Last Name");
  }

  @Test
  void shouldReturnEmployeeById() throws Exception {
    when(employeeService.getEmployeeById(1L)).thenReturn(mockResponseDto);

    mockMvc
        .perform(get("/api/v1/employees/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Doe"));
  }

  @Test
  void shouldReturnAllEmployees() throws Exception {
    when(employeeService.getAllEmployees()).thenReturn(List.of(mockResponseDto));
    mockMvc
        .perform(get("/api/v1/employees"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1)) // Asserting that the response has one item
        .andExpect(jsonPath("$[0].id").value(mockResponseDto.getId()))
        .andExpect(jsonPath("$[0].firstName").value(mockResponseDto.getFirstName()))
        .andExpect(jsonPath("$[0].lastName").value(mockResponseDto.getLastName()))
        .andExpect(jsonPath("$[0].email").value(mockResponseDto.getEmail()))
        .andExpect(jsonPath("$[0].phone").value(mockResponseDto.getPhone()));
  }

  @Test
  void shouldCreateEmployeeSuccessfully() throws Exception {
    when(employeeService.createEmployee(mockRequestDto)).thenReturn(mockResponseDto);
    mockMvc
        .perform(
            post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequestDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(mockResponseDto.getId()))
        .andExpect(jsonPath("$.firstName").value(mockResponseDto.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(mockResponseDto.getLastName()))
        .andExpect(jsonPath("$.email").value(mockResponseDto.getEmail()))
        .andExpect(jsonPath("$.phone").value(mockResponseDto.getPhone()));
  }

  @Test
  void shouldDeleteEmployeeSuccessfully() throws Exception {
    doNothing().when(employeeService).deleteEmployee(1L);
    mockMvc.perform(delete("/api/v1/employees/1")).andExpect(status().isNoContent());
  }

  @Test
  void shouldUpdateEmployeeSuccessfully() throws Exception {
    when(employeeService.updateEmployee(1L, mockRequestDto)).thenReturn(mockResponseDto);

    mockMvc
        .perform(
            put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequestDto)))
        .andExpect(jsonPath("$.id").value(mockResponseDto.getId()))
        .andExpect(jsonPath("$.firstName").value(mockResponseDto.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(mockResponseDto.getLastName()))
        .andExpect(jsonPath("$.email").value(mockResponseDto.getEmail()))
        .andExpect(jsonPath("$.phone").value(mockResponseDto.getPhone()));
  }

  @Test
  void shouldPartiallyUpdateEmployeeSuccessfully() throws Exception {
    mockResponseDto.setFirstName("New First Name");
    mockResponseDto.setLastName("New Last Name");
    when(employeeService.partiallyUpdateEmployee(1L, mockPatchRequestDto))
        .thenReturn(mockResponseDto);

    mockMvc
        .perform(
            patch("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockPatchRequestDto)))
        .andExpect(jsonPath("$.id").value(mockResponseDto.getId()))
        .andExpect(jsonPath("$.firstName").value(mockResponseDto.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(mockResponseDto.getLastName()))
        .andExpect(jsonPath("$.email").value(mockResponseDto.getEmail()))
        .andExpect(jsonPath("$.phone").value(mockResponseDto.getPhone()));
  }
}

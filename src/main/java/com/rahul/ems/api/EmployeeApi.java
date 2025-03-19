package com.rahul.ems.api;

import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Employee API", description = "Operations related to employees")
@RequestMapping("/api/v1")
public interface EmployeeApi {

    @Operation(summary = "Get an employee by ID", description = "Fetch an employee using their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found the employee"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/employees/{id}")
    ResponseEntity<?> getEmployee(@PathVariable Long id);

    @Operation(summary = "Get all employees", description = "Fetches all employees from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/employees")
    ResponseEntity<?> getAllEmployees();

    @Operation(summary = "Add a new employee", description = "Adds a new employee to the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee created successfully")
    })
    @PostMapping("/employees")
    ResponseEntity<?> createEmployee(
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee data for creation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeRequestDto.class),
                            examples = @ExampleObject(value = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phone\": \"9876543210\" }")
                    )
            )
            @RequestBody EmployeeRequestDto requestDto
    );

    @Operation(summary = "Delete an employee", description = "Deletes an employee using their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long empId);

    @Operation(summary = "Update an employee", description = "Updates an entire employee record based on ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/employees/{id}")
    ResponseEntity<?> updateEmployee(
            @Parameter(description = "ID of the employee to update", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto requestDto
    );

    @Operation(summary = "Partially update an employee", description = "Updates only the designation of an employee")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PatchMapping("/employees/{id}")
    ResponseEntity<?> partiallyUpdateEmployee(
            @PathVariable Long id, @Valid @RequestBody EmployeePatchRequestDto patchRequestDto
    );
}

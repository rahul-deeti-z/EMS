package com.rahul.ems.mapper;

import com.rahul.ems.dto.EmployeePatchRequestDto;
import com.rahul.ems.dto.EmployeeRequestDto;
import com.rahul.ems.dto.EmployeeResponseDto;
import com.rahul.ems.entity.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
  Employee toEntity(EmployeeRequestDto requestDto);

  EmployeeResponseDto toDto(Employee employee);

  // Mapping DTO → Entity (for updates)
  @Mapping(target = "id", ignore = true) // Ensure ID is not overwritten
  void updateEmployeeFromDto(EmployeeRequestDto dto, @MappingTarget Employee entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void partiallyUpdateEmployeeFromDto(
      EmployeePatchRequestDto patchRequestDto, @MappingTarget Employee entity);

  List<EmployeeResponseDto> toDtoList(List<Employee> employees);
}

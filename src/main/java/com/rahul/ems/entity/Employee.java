package com.rahul.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name")
  @NotNull
  @NotBlank
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  @NotBlank
  private String lastName;

  @Column(name = "email")
  @NotNull
  @NotBlank
  private String email;

  @Column(name = "phone")
  @NotNull
  @NotBlank
  private String phone;
}

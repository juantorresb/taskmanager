package com.ionix.assessment.taskmanager.dto;

import com.ionix.assessment.taskmanager.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String cellPhone;
  private boolean isEnabled;
  private Role role;
}

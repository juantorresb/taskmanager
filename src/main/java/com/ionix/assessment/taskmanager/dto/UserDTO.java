package com.ionix.assessment.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {	
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String cellPhone;
	private String password;
	private boolean enabled;
	private String role;
}



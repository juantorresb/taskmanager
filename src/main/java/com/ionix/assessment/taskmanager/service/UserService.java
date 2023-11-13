package com.ionix.assessment.taskmanager.service;

import java.security.Principal;
import java.util.List;

import com.ionix.assessment.taskmanager.dto.ChangePasswordRequest;
import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.dto.UserDTO;

public interface UserService {		
	
	UserDTO createUser(UserDTO userDTO);

	UserDTO findByUserById(Long id);

	UserDTO findByUserByEmail(String email);

	UserDTO updateUser(UserDTO userDTO);

    void deleteUser(long id);
    
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    List<UserDTO> getAllUsers();

    List<TaskDTO> getAllTasksByUser(Integer userId);
	
}

package com.ionix.assessment.taskmanager.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ionix.assessment.taskmanager.dto.ChangePasswordRequest;
import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.dto.UserDTO;
import com.ionix.assessment.taskmanager.exception.ResourceNotFoundException;
import com.ionix.assessment.taskmanager.exception.ResourceRestrictionException;
import com.ionix.assessment.taskmanager.exception.UserAlreadyExistsExcepion;
import com.ionix.assessment.taskmanager.model.Role;
import com.ionix.assessment.taskmanager.model.Task;
import com.ionix.assessment.taskmanager.model.User;
import com.ionix.assessment.taskmanager.repository.TaskRepository;
import com.ionix.assessment.taskmanager.repository.UserRepository;
import com.ionix.assessment.taskmanager.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDTO createUser(UserDTO pUserDTO) {
		validateUserRestrictions(pUserDTO);
		User user = mapUser(pUserDTO);
		
		user = userRepository.save(user);
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCellPhone(), "", user.isEnabled(), user.getRole().toString());
	}

	@Override
	public UserDTO findByUserById(Long id) {
		User user = this.findUserById(id);
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCellPhone(), "", user.isEnabled(), user.getRole().toString());
	}
	
	@Override
	public UserDTO findByUserByEmail(String email) {
		User user = this.findUserByEmail(email);
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCellPhone(), "", user.isEnabled(), user.getRole().toString());
	}

	@Override
	public UserDTO updateUser(UserDTO pUserDTO) {
		User user = findUserByEmail(pUserDTO.getEmail());
		validateAdminRole(pUserDTO);
		user = mapUser(pUserDTO);
		userRepository.save(user);
		return pUserDTO;
	}

	@Override
	public void deleteUser(long id) {
		User user = this.findUserById(id);
		//TODO validate User status
		userRepository.delete(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDTO> UserDTOList = new ArrayList<UserDTO>();
		for (User user : users) {
			UserDTO UserDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCellPhone(), "", user.isEnabled(), user.getRole().toString());
			UserDTOList.add(UserDTO);
		}
		return UserDTOList;
	}

	@Override
	public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
		var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setEnabled(true);

        // save the new password
        userRepository.save(user);
	}	

	@Override
	public List<TaskDTO> getAllTasksByUser(Integer userId) {
        List<Task> taskList = new ArrayList<Task>();
		User user = this.findUserById(userId);
		taskRepository.findByAssignedUser(user).forEach(taskList::add); 
		return taskList.stream().map(task -> new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getAssignedUser().getId()))
	                .collect(Collectors.toList());
	}
	
	
	/**
	 * Validate User restriction to create one.
	 * @param pUserDTO
	 */
	private void validateUserRestrictions(UserDTO pUserDTO) {
		if (userRepository.findByEmail(pUserDTO.getEmail()).isPresent()) {
			throw new UserAlreadyExistsExcepion("User already exist with username: " + pUserDTO.getEmail());
		}

		validateAdminRole(pUserDTO);
	}

	/**
	 * Validate ADMIN Role
	 * @param pUserDTO
	 */
	private void validateAdminRole(UserDTO pUserDTO) {
		if (Role.ADMIN.toString().equals(pUserDTO.getRole()))
		{
			throw new ResourceRestrictionException("Admin Users cannot be created: " + pUserDTO.getEmail());
		}
	}

	private User findUserById(long pId) {
		return userRepository.findById(pId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	private User findUserByEmail(String pEmail) {
		return userRepository.findByEmail(pEmail)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
	
	private User mapUser(UserDTO pUserDTO) {
		return User.builder()
				.firstName(pUserDTO.getFirstName())
				.lastName(pUserDTO.getLastName())
				.email(pUserDTO.getEmail())
				.cellPhone(pUserDTO.getCellPhone())
				.password(pUserDTO.getPassword())
				.role(Role.valueOf(pUserDTO.getRole()))				
				.build();
	}

	
}

package com.ionix.assessment.taskmanager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ionix.assessment.taskmanager.dto.ChangePasswordRequest;
import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.dto.UserDTO;
import com.ionix.assessment.taskmanager.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
		 return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTOR')")
	@GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasksByUserID(@PathVariable("userId") Integer userId) {
		 return new ResponseEntity<>(userService.getAllTasksByUser(userId), HttpStatus.OK);
    }

	@PreAuthorize("hasRole('ADMIN') OR hasRole('AUDITOR') OR hasRole('EXECUTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long userId) {
    	return new ResponseEntity<>(userService.findByUserById(userId), HttpStatus.OK);
    }
    
	@PreAuthorize("hasRole('ADMIN') OR hasRole('AUDITOR') OR hasRole('EXECUTOR')")
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("email") String pEmail) {
    	return new ResponseEntity<>(userService.findByUserByEmail(pEmail), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
    	return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
    	 return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") int userId) {
    	userService.deleteUser(userId);
    }
    
	@PreAuthorize("hasRole('ADMIN')  OR hasRole('AUDITOR') OR hasRole('EXECUTOR')")
    @PatchMapping
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordDTO, Principal connectedUser) {
        userService.changePassword(changePasswordDTO, connectedUser);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }
}

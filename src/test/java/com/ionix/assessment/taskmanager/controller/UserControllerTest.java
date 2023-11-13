package com.ionix.assessment.taskmanager.controller;

import static com.ionix.assessment.taskmanager.testutil.TestUtil.buildnewUserDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ionix.assessment.taskmanager.dto.UserDTO;
import com.ionix.assessment.taskmanager.service.UserService;

@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getAllUsers() {
        // Mock data
        List<UserDTO> mockUser = Collections.singletonList(buildnewUserDTO(1, "john.doe@mail.com"));
        
        // Mocking behavior of Userservice
        when(userService.getAllUsers()).thenReturn(mockUser);
        
        // Performing the actual method call
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verifying that Userservice.getAll() was called once
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById() {
        // Mock data
        long userId = 1;
        UserDTO mockUser = buildnewUserDTO((int)userId, "john.doe@mail.com");

        // Mocking behavior of Userservice
        when(userService.findByUserById(userId)).thenReturn(mockUser);

        // Performing the actual method call
        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody());

        // Verifying that Userservice.findById() was called once with the correct argument
        verify(userService, times(1)).findByUserById(userId);
    }
    
    @Test
    void createUser() {
        // Mock data
        int userId = 3;
        UserDTO userDTO = buildnewUserDTO(userId, "john.doe@mail.com");
        UserDTO createdUser = buildnewUserDTO(userId, "john.doe@mail.com");

        // Mocking behavior of Userservice
        when(userService.createUser(userDTO)).thenReturn(createdUser);

        // Performing the actual method call
        ResponseEntity<UserDTO> response = userController.create(userDTO);

        // Verifying the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUser, response.getBody());

        // Verifying that Userservice.create() was called once with the correct argument
        verify(userService, times(1)).createUser(userDTO);
    }
    
    @Test
    void updateUser() {
        // Mock data
    	int userId = 2;
        UserDTO userDTO = buildnewUserDTO(userId, "john.doe@mail.com");
        UserDTO updatedUser = buildnewUserDTO(userId, "john.doe@mail.com");

        // Mocking behavior of Userservice
        when(userService.updateUser(userDTO)).thenReturn(updatedUser);

        // Performing the actual method call
        ResponseEntity<UserDTO> response = userController.update(userDTO);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());

        // Verifying that Userservice.update() was called once with the correct argument
        verify(userService, times(1)).updateUser(userDTO);
    }
    
    @Test
    void deleteUser() {
        // Mock data
        int userID = 1;

        // Performing the actual method call
        userController.delete(userID);

        // Verifying that Userservice.delete() was called once with the correct argument
        verify(userService, times(1)).deleteUser(userID);
    }
    


    // Similar tests can be written for other methods (create, update, delete)
}
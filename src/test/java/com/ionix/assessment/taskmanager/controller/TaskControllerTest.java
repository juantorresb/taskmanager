package com.ionix.assessment.taskmanager.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.service.TaskService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static com.ionix.assessment.taskmanager.testutil.TestUtil.buildnewTaskDTO;

@SpringBootTest
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void getAllTasks() {
        // Mock data
        List<TaskDTO> mockTasks = Collections.singletonList(buildnewTaskDTO(0L));
        
        // Mocking behavior of taskService
        when(taskService.getAll()).thenReturn(mockTasks);
        
        // Performing the actual method call
        ResponseEntity<List<TaskDTO>> response = taskController.getAll();

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTasks, response.getBody());

        // Verifying that taskService.getAll() was called once
        verify(taskService, times(1)).getAll();
    }

    @Test
    void getTaskById() {
        // Mock data
        long taskId = 1L;
        TaskDTO mockTask = buildnewTaskDTO(taskId);

        // Mocking behavior of taskService
        when(taskService.findById(taskId)).thenReturn(mockTask);

        // Performing the actual method call
        ResponseEntity<TaskDTO> response = taskController.getTaskById(taskId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTask, response.getBody());

        // Verifying that taskService.findById() was called once with the correct argument
        verify(taskService, times(1)).findById(taskId);
    }
    
    @Test
    void createTask() {
        // Mock data
        long taskId = 3L;
        TaskDTO taskDTO = buildnewTaskDTO(taskId);
        TaskDTO createdTask = buildnewTaskDTO(taskId);

        // Mocking behavior of taskService
        when(taskService.create(taskDTO)).thenReturn(createdTask);

        // Performing the actual method call
        ResponseEntity<TaskDTO> response = taskController.create(taskDTO);

        // Verifying the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdTask, response.getBody());

        // Verifying that taskService.create() was called once with the correct argument
        verify(taskService, times(1)).create(taskDTO);
    }
    
    @Test
    void updateTask() {
        // Mock data
        long taskId = 2L;
        TaskDTO taskDTO = buildnewTaskDTO(taskId);
        TaskDTO updatedTask = buildnewTaskDTO(taskId);

        // Mocking behavior of taskService
        when(taskService.update(taskDTO)).thenReturn(updatedTask);

        // Performing the actual method call
        ResponseEntity<TaskDTO> response = taskController.update(taskDTO);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());

        // Verifying that taskService.update() was called once with the correct argument
        verify(taskService, times(1)).update(taskDTO);
    }
    
    @Test
    void deleteTask() {
        // Mock data
        int taskId = 1;

        // Performing the actual method call
        taskController.delete(taskId);

        // Verifying that taskService.delete() was called once with the correct argument
        verify(taskService, times(1)).delete(taskId);
    }
    


    // Similar tests can be written for other methods (create, update, delete)
}
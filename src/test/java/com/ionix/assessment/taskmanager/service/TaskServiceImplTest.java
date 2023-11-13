package com.ionix.assessment.taskmanager.service;

import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.exception.ResourceNotFoundException;
import com.ionix.assessment.taskmanager.model.Task;
import com.ionix.assessment.taskmanager.model.User;
import com.ionix.assessment.taskmanager.repository.TaskRepository;
import com.ionix.assessment.taskmanager.repository.UserRepository;
import com.ionix.assessment.taskmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.ionix.assessment.taskmanager.testutil.TestUtil.buildnewTaskDTO;
import static com.ionix.assessment.taskmanager.testutil.TestUtil.buildnewUser;
import static com.ionix.assessment.taskmanager.testutil.TestUtil.buildnewTask;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask() {
    	long userID = 1;
        // Mock data
        TaskDTO taskDTO = buildnewTaskDTO(1L, "Task Title", "Task Description", null, "Pending", userID);
        User user = buildnewUser(userID,"john.doe@example.com");
        Task task = new Task(1L, "Task Title", "Task Description", null, "Pending", user);
        

        Optional<User> returnUser = Optional.of(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(returnUser);
        
        // Mocking behavior of userRepository
        //when(userRepository.findById(userID)).thenReturn(Optional.of(user));
        // Mocking behavior of taskRepository
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Performing the actual method call
        TaskDTO createdTaskDTO = taskService.create(taskDTO);

        // Verifying the result
        assertNotNull(createdTaskDTO);
        assertEquals(taskDTO, createdTaskDTO);

        // Verifying that userRepository.findById() was called once with the correct argument
        verify(userRepository, times(1)).findById(1L);

        // Verifying that taskRepository.save() was called once with the correct argument
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void findById() {
        // Mock data
        long taskId = 1L;
        Task task = buildnewTask(1L, new User());

        // Mocking behavior of taskRepository
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Performing the actual method call
        TaskDTO foundTaskDTO = taskService.findById(taskId);

        // Verifying the result
        assertNotNull(foundTaskDTO);
        assertEquals(taskId, foundTaskDTO.id());

        // Verifying that taskRepository.findById() was called once with the correct argument
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void updateTask() {
        // Mock data
        TaskDTO taskDTO = buildnewTaskDTO(1L, "Updated Task Title", "Updated Task Description", null, "Updated", 2L);
        User user =  buildnewUser(2L,"juan.doe@example.com");
        Task existingTask = buildnewTask(1L, user);
        Task updatedTask = new Task(1L, "Updated Task Title", "Updated Task Description", null, "Updated", user);

        // Mocking behavior of userRepository
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Mocking behavior of taskRepository
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Performing the actual method call
        TaskDTO updatedTaskDTO = taskService.update(taskDTO);

        // Verifying the result
        assertNotNull(updatedTaskDTO);
        assertEquals(taskDTO, updatedTaskDTO);

        // Verifying that userRepository.findById() was called once with the correct argument
        verify(userRepository, times(1)).findById(2L);

        // Verifying that taskRepository.findById() was called once with the correct argument
        verify(taskRepository, times(1)).findById(1L);

        // Verifying that taskRepository.save() was called once with the correct argument
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask() {
        // Mock data
        long taskId = 1L;
        Task existingTask = new Task(1L, "Task Title", "Task Description", null, "Pending", new User());

        // Mocking behavior of taskRepository
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(existingTask));

        // Performing the actual method call
        assertDoesNotThrow(() -> taskService.delete(taskId));

        // Verifying that taskRepository.findById() was called once with the correct argument
        verify(taskRepository, times(1)).findById(1L);

        // Verifying that taskRepository.delete() was called once with the correct argument
        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void getAllTasks() {
        // Mock data
        List<Task> taskList = new ArrayList<>();
        User user = buildnewUser(1L, "john.doe@example.com");
        taskList.add(new Task(1L, "Task 1", "Description 1", null, "Pending", user));
        taskList.add(new Task(2L, "Task 2", "Description 2", null, "Completed", user));

        // Mocking behavior of taskRepository
        when(taskRepository.findAll()).thenReturn(taskList);

        // Performing the actual method call
        List<TaskDTO> allTasksDTO = taskService.getAll();

        // Verifying the result
        assertNotNull(allTasksDTO);
        assertEquals(taskList.size(), allTasksDTO.size());

        // Verifying that taskRepository.findAll() was called once
        verify(taskRepository, times(1)).findAll();
    }
}
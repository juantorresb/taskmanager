package com.ionix.assessment.taskmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

	private final TaskService taskService;

	@PreAuthorize("hasRole('ADMIN') OR hasRole('AUDITOR')")
	@GetMapping
    public ResponseEntity<List<TaskDTO>> getAll() {
		 return new ResponseEntity<>(taskService.getAll(), HttpStatus.OK);
    }
	
	@PreAuthorize("hasRole('ADMIN')  OR hasRole('AUDITOR') OR hasRole('EXECUTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") Long taskId) {
    	return new ResponseEntity<>(taskService.findById(taskId), HttpStatus.OK);
    }
    
    // Create a new task, accessible only for users with the profile "EXECUTOR"
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.create(taskDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('EXECUTOR')")
    @PutMapping
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO taskDTO) {
    	 return new ResponseEntity<>(taskService.update(taskDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") int taskId) {
    	taskService.delete(taskId);
    }
}

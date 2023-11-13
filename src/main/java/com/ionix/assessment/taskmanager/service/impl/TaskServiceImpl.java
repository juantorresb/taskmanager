package com.ionix.assessment.taskmanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.exception.ResourceNotFoundException;
import com.ionix.assessment.taskmanager.model.Task;
import com.ionix.assessment.taskmanager.model.User;
import com.ionix.assessment.taskmanager.repository.TaskRepository;
import com.ionix.assessment.taskmanager.repository.UserRepository;
import com.ionix.assessment.taskmanager.service.TaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	@Override
	public TaskDTO create(TaskDTO pTaskDTO) {
		User user = this.findUserById(pTaskDTO.idUsuarioAsignado());
		Task task = Task.builder()
				.title(pTaskDTO.title())
				.description(pTaskDTO.description())
				.status(pTaskDTO.status())
				.dueDate(pTaskDTO.dueDate())
				.assignedUser(user)
				.build();

		task = taskRepository.save(task);
		return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), user.getId());
	}

	@Override
	public TaskDTO findById(long pId) {
		Task task = this.findTaskById(pId);
		return  new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getAssignedUser().getId());
	}

	@Override
	public TaskDTO update(TaskDTO pTaskDTO) {
		Task task = this.findTaskById(pTaskDTO.id());
		User user = this.findUserById(pTaskDTO.idUsuarioAsignado());
		task.setTitle(pTaskDTO.title());
		task.setDescription(pTaskDTO.description());
		task.setDueDate(pTaskDTO.dueDate());
		task.setStatus(pTaskDTO.status());
		task.setAssignedUser(user);
		task = taskRepository.save(task);
		return pTaskDTO;
	}

	@Override
	public void delete(long pId) {
		Task task = this.findTaskById(pId);
		//TODO validate Task status
		taskRepository.delete(task);
	}

	@Override
	public List<TaskDTO> getAll() {
        List<Task> taskList = new ArrayList<Task>();
        taskRepository.findAll().forEach(taskList::add);
        return taskList.stream().map(task -> new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus(), task.getAssignedUser().getId()))
                .collect(Collectors.toList());
	}
	
	public Task findTaskById(long pId) {
		return taskRepository.findById(pId)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found"));
	}
	
	public User findUserById(long pId) {
		return userRepository.findById(pId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

}

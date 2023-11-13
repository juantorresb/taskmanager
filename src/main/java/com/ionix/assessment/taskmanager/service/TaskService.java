package com.ionix.assessment.taskmanager.service;

import java.util.List;

import com.ionix.assessment.taskmanager.dto.TaskDTO;


public interface TaskService {

	TaskDTO create(TaskDTO taskDTO);

	TaskDTO findById(long id);

	TaskDTO update(TaskDTO taskDTO);

    void delete(long id);

    List<TaskDTO> getAll();
}

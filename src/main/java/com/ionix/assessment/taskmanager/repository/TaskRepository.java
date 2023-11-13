package com.ionix.assessment.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ionix.assessment.taskmanager.model.Task;
import com.ionix.assessment.taskmanager.model.User;


public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByAssignedUser(User assignedUser);
}

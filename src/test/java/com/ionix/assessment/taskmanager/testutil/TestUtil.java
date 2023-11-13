package com.ionix.assessment.taskmanager.testutil;

import java.util.Date;

import com.ionix.assessment.taskmanager.dto.TaskDTO;
import com.ionix.assessment.taskmanager.dto.UserDTO;
import com.ionix.assessment.taskmanager.model.Role;
import com.ionix.assessment.taskmanager.model.Task;
import com.ionix.assessment.taskmanager.model.User;

public final class TestUtil {

	public static TaskDTO buildnewTaskDTO(long taskId) {
		return buildnewTaskDTO(taskId, "Title", "Description", new Date(), "Created", 1234L);
	}
	
	public static TaskDTO buildnewTaskDTO(long taskId, String title, String Description, Date dueDate, String status, Long userAssigned) {
		return new TaskDTO(taskId, title, Description, dueDate, status, userAssigned.intValue());
	}
	
	public static Task buildnewTask(long taskId, User user) {
		return new Task(taskId, "Task Title", "Task Description", null, "Pending", user);
	}
	
	public static User buildnewUser(Long userId, String email) {
		return User.builder().id(userId.intValue())
				.firstName("John")
				.lastName("Doe")
				.email(email)
				.isEnabled(true)
				.password("1234")
				.role(Role.ADMIN)
				.build();				
	}
	
	public static UserDTO buildnewUserDTO(Integer userId, String email) {
		return new UserDTO(userId, "John", "Doe", email, "11111", "1234", true, "ADMIN");
	}
	
	

}

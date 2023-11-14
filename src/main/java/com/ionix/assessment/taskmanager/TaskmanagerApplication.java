package com.ionix.assessment.taskmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ionix.assessment.taskmanager.dto.RegisterRequest;
import com.ionix.assessment.taskmanager.model.Role;
import com.ionix.assessment.taskmanager.service.impl.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j 
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			log.info("Admin token: " + service.register(admin).getAccessToken());

			var executor = RegisterRequest.builder()
					.firstname("Executor")
					.lastname("Executor")
					.email("executor@mail.com")
					.password("password")
					.role(Role.EXECUTOR)
					.build();
			log.info("Executor token: " + service.register(executor).getAccessToken());
			
			var auditor = RegisterRequest.builder()
					.firstname("Auditor")
					.lastname("Auditor")
					.email("auditor@mail.com")
					.password("password")
					.role(Role.AUDITOR)
					.build();
			log.info("Executor token: " + service.register(auditor).getAccessToken());
		};
	}
}

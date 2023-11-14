package com.ionix.assessment.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ionix.assessment.taskmanager.dto.AuthenticationRequest;
import com.ionix.assessment.taskmanager.dto.AuthenticationResponse;
import com.ionix.assessment.taskmanager.service.impl.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;
  	
  	@PostMapping("/authenticate")
  	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
  		return ResponseEntity.ok(service.authenticate(request));
  	}
}

package com.ionix.assessment.taskmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ionix.assessment.taskmanager.dto.AuthenticationRequest;
import com.ionix.assessment.taskmanager.dto.AuthenticationResponse;
import com.ionix.assessment.taskmanager.dto.RegisterRequest;
import com.ionix.assessment.taskmanager.service.impl.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;

  	@PostMapping("/register")
  	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
  		return ResponseEntity.ok(service.register(request));
  	}
  	
  	@PostMapping("/authenticate")
  	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
  		return ResponseEntity.ok(service.authenticate(request));
  	}

  	@PostMapping("/refresh-token")
  	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
  		service.refreshToken(request, response);
  	}


}

package com.ionix.assessment.taskmanager.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
	
	String extractUserName(String token);

	String generateToken(UserDetails userDetails);
	
	String generateRefreshToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}

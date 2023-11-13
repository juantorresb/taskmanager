package com.ionix.assessment.taskmanager.security.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ionix.assessment.taskmanager.repository.TokenRepository;
import com.ionix.assessment.taskmanager.service.impl.JWTServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private final JWTServiceImpl jwtService;
	private final UserDetailsService userDetailsService;
	private final TokenRepository tokenRepository;
	private final ObjectMapper mapper;
	
    //private final JWTokenUtil jwtUtil;     
    
    @Override
    protected void doFilterInternal(
    		@NonNull HttpServletRequest request, 
    		@NonNull HttpServletResponse response, 
    		@NonNull FilterChain filterChain) throws ServletException, IOException {
    	
    	 if (request.getServletPath().contains("/api/v1/auth")) {
    		 filterChain.doFilter(request, response);
    		 return;
    	 }
    	
        try {
	    	final String userEmail;
	        final String accessToken = JWTokenUtil.resolveToken(request);
	        if (accessToken == null) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	        
	        userEmail = jwtService.extractUserName(accessToken);
	        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        	UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
	        	var isTokenValid = tokenRepository.findByToken(accessToken)
	        			.map(t -> !t.isExpired() && !t.isRevoked())
	        			.orElse(false);
	        	
	        	if (jwtService.isTokenValid(accessToken, userDetails) && isTokenValid) {
	        		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	        				userDetails,
	        				null,
	        				userDetails.getAuthorities()
	        		);
	            
	        		authToken.setDetails(
	        				new WebAuthenticationDetailsSource().buildDetails(request));                
	        		SecurityContextHolder.getContext().setAuthentication(authToken);
	        	}
	        }
	        
	        filterChain.doFilter(request, response);
            
            /*
            final String userEmail = jwtService.extractUserName(accessToken);
            
            
            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null & jwtUtil.validateClaims(claims)){
                String email = claims.getSubject();
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email,"",new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }*/

        } catch (Exception e){
        	Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details",e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
        }
        
        filterChain.doFilter(request, response);
    }
    
}

package com.ionix.assessment.taskmanager.security.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static com.ionix.assessment.taskmanager.model.Role.ADMIN;
import static com.ionix.assessment.taskmanager.model.Role.EXECUTOR;
import static com.ionix.assessment.taskmanager.model.Role.AUDITOR;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.PUT;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
			"/h2-console/", "/h2-console/**",
			 "/swagger-resources",
	            "/swagger-resources/**",
	            "/configuration/ui",
	            "/configuration/security",
	            "/swagger-ui/**",
	            "/webjars/**",
	            "/swagger-ui/index.html"};
	
	private final JWTAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;
	
	@Bean
	public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        	.csrf(AbstractHttpConfigurer::disable)
        	.authorizeHttpRequests(req ->
        		req.requestMatchers(WHITE_LIST_URL).permitAll())
        	.authorizeHttpRequests(req ->
        		req.requestMatchers("/api/v1/users/**").hasAuthority(ADMIN.name())
	        		.requestMatchers(GET, "/api/v1/users/**").hasAnyAuthority(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
	        		.requestMatchers(POST, "/api/v1/users/**").hasAnyAuthority(ADMIN.name())
	        		.requestMatchers(PUT, "/api/v1/users/**").hasAnyAuthority(ADMIN.name())
	        		.requestMatchers(DELETE, "/api/v1/users/**").hasAnyAuthority(ADMIN.name())
	        		.requestMatchers(PATCH, "/api/v1/users/**").hasAnyAuthority(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
	        		// TASKS
        			.requestMatchers(GET, "/api/v1/tasks/**").hasAnyAuthority(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
            		.requestMatchers(POST, "/api/v1/tasks/**").hasAuthority(ADMIN.name())
            		.requestMatchers(PUT, "/api/v1/tasks/**").hasAnyAuthority(ADMIN.name(), EXECUTOR.name())
            		.requestMatchers(DELETE, "/api/v1/tasks/**").hasAuthority(ADMIN.name())
        		
            		/*.requestMatchers("/api/v1/tasks/**").hasAnyRole(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
            		.requestMatchers(GET, "/api/v1/tasks/**").hasAnyAuthority(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
            		.requestMatchers(POST, "/api/v1/tasks/**").hasAnyAuthority(ADMIN.name())
            		.requestMatchers(PUT, "/api/v1/tasks/**").hasAnyAuthority(ADMIN.name(), EXECUTOR.name())
            		.requestMatchers(DELETE, "/api/v1/tasks/**").hasAnyAuthority(ADMIN.name())
            		
            		.requestMatchers("/api/v1/users/**").hasAnyRole(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
            		.requestMatchers(GET, "/api/v1/users/**").hasAnyAuthority(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())
            		.requestMatchers(POST, "/api/v1/users/**").hasAnyAuthority(ADMIN.name())
            		.requestMatchers(PUT, "/api/v1/users/**").hasAnyAuthority(ADMIN.name())
            		.requestMatchers(DELETE, "/api/v1/users/**").hasAnyAuthority(ADMIN.name())
            		.requestMatchers(PATCH, "/api/v1/users/**").hasAnyAuthority(ADMIN.name(), AUDITOR.name(), EXECUTOR.name())*/
            	.anyRequest()
            	.authenticated()
        	)
        	.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        	.authenticationProvider(authenticationProvider)
        	.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        	.logout(logout ->
                   logout.logoutUrl("/api/v1/auth/logout")
                           .addLogoutHandler(logoutHandler)
                           .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                   		);
		
		return http.build();
	}

}

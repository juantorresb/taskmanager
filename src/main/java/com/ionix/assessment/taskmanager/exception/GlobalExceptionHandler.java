package com.ionix.assessment.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(value = {UnprocessableEntityException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse unprocessedEntityException(UnprocessableEntityException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getDescription(false));
    }
    
    @ExceptionHandler(value = {UserAlreadyExistsExcepion.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse userAlreadyExistsExcepion(UserAlreadyExistsExcepion ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getDescription(false));
    }
    
    @ExceptionHandler(value = {ResourceRestrictionException.class})
    @ResponseStatus(value = HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public ErrorResponse resourceRestrictionException(ResourceRestrictionException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(), request.getDescription(false));
    }
    
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
    	ErrorResponse errorResponse = new ErrorResponse("Access Denied", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);    	
    }
}

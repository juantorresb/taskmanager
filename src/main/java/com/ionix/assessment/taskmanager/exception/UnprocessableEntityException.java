package com.ionix.assessment.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public UnprocessableEntityException(String message) {
        super(message);
    }
}

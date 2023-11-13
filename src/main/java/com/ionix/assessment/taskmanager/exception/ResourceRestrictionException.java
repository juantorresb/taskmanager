package com.ionix.assessment.taskmanager.exception;

public class ResourceRestrictionException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ResourceRestrictionException(final String message) {
        super(message);
    }
}

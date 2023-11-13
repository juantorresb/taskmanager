package com.ionix.assessment.taskmanager.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    private transient List<ErrorResponse> violations = new ArrayList<>();

    public List<ErrorResponse> getViolations() {
        return violations;
    }

    public void addViolations(ErrorResponse violation) {
        this.violations.add(violation);
    }
}

package com.ionix.assessment.taskmanager.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskDTO(@JsonProperty("id") Long id, String title, String description, Date dueDate, String status, Integer idUsuarioAsignado) {

}

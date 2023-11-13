package com.ionix.assessment.taskmanager.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "title", nullable = false)
	private String title;
    
    @Column(name = "description", nullable = false)
	private String description;
    
    @Column(name = "due_date", nullable = false)
	private Date dueDate;
    
    @Column(name = "status", nullable = false)
	private String status;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;
}

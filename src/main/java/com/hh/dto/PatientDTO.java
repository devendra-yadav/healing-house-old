package com.hh.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PatientDTO {
	@NotNull(message = "Patient name cant be blank")
	private String name;
	private LocalDate dateOfBirth;
	private String mobile;
	private String email;
	private LocalDateTime firstVisitDate = LocalDateTime.now();
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String remarks;
}

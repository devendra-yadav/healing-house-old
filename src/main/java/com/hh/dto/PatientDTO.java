package com.hh.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PatientDTO {
	
	private String name;
	private LocalDate dateOfBirth;
	private String mobile;
	private String email;
	private LocalDate firstVisitDate = LocalDate.now();
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	
}

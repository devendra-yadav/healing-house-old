package com.hh.entity;

import java.time.LocalDate;

import com.hh.dto.PatientDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private LocalDate dateOfBirth;
	private String mobile;
	private String email;
	private LocalDate firstVisitDate;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String remarks;
	
	public Patient(PatientDTO patientDto) {
		this.name = patientDto.getName();
		this.dateOfBirth = patientDto.getDateOfBirth();
		this.mobile = patientDto.getMobile();
		this.email = patientDto.getEmail();
		this.firstVisitDate = patientDto.getFirstVisitDate();
		this.addressLine1 = patientDto.getAddressLine1();
		this.addressLine2 = patientDto.getAddressLine2();
		this.city = patientDto.getCity();
		this.state = patientDto.getState();
		this.country = patientDto.getCountry();
		this.remarks = patientDto.getRemarks();
	}
	
}

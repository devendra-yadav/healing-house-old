package com.hh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.dto.PatientDTO;

@Controller
@RequestMapping("/patients")
public class PatientsController {
	
	@GetMapping("/")
	public String patients() {
		return "patients/patient_home";
	}
	
	@GetMapping("/new_patient_form")
	public String newPatientForm(@ModelAttribute("patient") PatientDTO patientDto) {
		patientDto = new PatientDTO();
		
		return "patients/new_patient_form";
	}
}

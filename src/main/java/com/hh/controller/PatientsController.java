package com.hh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.dto.PatientDTO;
import com.hh.entity.Patient;
import com.hh.repository.PatientRepository;

@Controller
@RequestMapping("/patients")
public class PatientsController {

	private Logger logger = LoggerFactory.getLogger(PatientsController.class);
	
	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping("/")
	public String patients() {
		return "patients/patient_home";
	}
	
	@GetMapping("/add_patient_form")
	public String addPatientForm(@ModelAttribute("patient") PatientDTO patientDto) {
		patientDto = new PatientDTO();
		return "patients/new_patient_form";
	}
	
	@PostMapping("/add_patient")
	public String addPatient(@ModelAttribute("pattient") PatientDTO patientDto) {
		logger.info("New patient to add : "+patientDto);
		Patient patient = new Patient(patientDto);
		patient = patientRepository.save(patient);
		logger.info("Saved New Patient. Patient ID : "+patient.getId());
		return "redirect:/";
	}
}

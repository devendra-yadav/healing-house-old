package com.hh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.dto.PatientDTO;
import com.hh.entity.Package;
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
		//patientDto = new PatientDTO();
		return "patients/add_patient_form";
	}
	
	@PostMapping("/add_patient")
	public String addPatient(@ModelAttribute("patient") PatientDTO patientDto) {
		logger.info("New patient to add : "+patientDto);
		Patient patient = new Patient(patientDto);
		patient = patientRepository.save(patient);
		logger.info("Saved New Patient. Patient ID : "+patient.getId());
		return "redirect:/";
	}
	
	@GetMapping("/view_patients")
	public String viewPatients(Model model) {
		List<Patient> allPatients = patientRepository.findAll();
		model.addAttribute("allPatients", allPatients);
		return "patients/all_patients";
	}
	
	@GetMapping("/view/{patient_id}")
	public String getPatientById(@PathVariable("patient_id") Integer patientId, Model model) {
		logger.info("Fetching patient id "+patientId);
		Patient patient = patientRepository.findById(patientId).get();
		logger.info("Patient fetched : "+patient);
		model.addAttribute("patient", patient);
		return "/patients/patient_details";
	}
	
	@GetMapping("/delete/{patient_id}")
	public String deletePackage(@PathVariable("patient_id") Integer patientId,  Model model) {
		Patient patient = patientRepository.findById(patientId).get();
		if(patient!=null) {
			patientRepository.delete(patient);
			logger.info("Patient '"+patient.getName()+"' deleted.");
		}else {
			logger.warn("Invalid patient id "+patientId+". Cant delete!");
		}
		return "redirect:/patients/view_patients";
	}
}

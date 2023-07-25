package com.hh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.dto.PatientDTO;
import com.hh.entity.Package;
import com.hh.entity.Patient;
import com.hh.entity.Treatment;
import com.hh.repository.PatientRepository;
import com.hh.repository.TreatmentRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/patients")
public class PatientsController {

	private Logger logger = LoggerFactory.getLogger(PatientsController.class);
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private TreatmentRepository treatmentRepository;
	
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
	public String addPatient(@ModelAttribute("patient") @Valid PatientDTO patientDto, BindingResult bindingResult,  Model model) {
		logger.info("New patient to add : "+patientDto);
		
		if(bindingResult.hasErrors()) {
			logger.warn("Pation have invalid values. "+bindingResult);
			return "patients/add_patient_form";
		}
		
		if(patientDto.getHowDidYouFindUs().equals(",")) {
			patientDto.setHowDidYouFindUs("");
		}
		//Remove extra comma from the end
		if(patientDto.getHowDidYouFindUs().endsWith(",")) {
			int lengthOfHowDidYouFindUs=patientDto.getHowDidYouFindUs().length();
			patientDto.setHowDidYouFindUs(patientDto.getHowDidYouFindUs().substring(0, lengthOfHowDidYouFindUs-1));
		}
		
		Patient patient = new Patient(patientDto);
		patient = patientRepository.save(patient);
		logger.info("Saved New Patient. Patient ID : "+patient.getId());
		return "redirect:/patients/view_patients?recentlyAddedPatientId="+patient.getId();
	}
	
	@GetMapping("/view_patients")
	public String viewPatients(@RequestParam(name = "recentlyAddedPatientId", required=false) Integer recentlyAddedPatientId , @RequestParam(name = "recentlyEditedPatientId", required=false) Integer recentlyEditedPatientId, @RequestParam(name = "recentlyDeletedPatientId", required=false) Integer recentlyDeletedPatientId, @RequestParam(name = "recentlyDeletedPatientName", required=false) String recentlyDeletedPatientName, Model model) {
		List<Patient> allPatients = patientRepository.findAll();
		model.addAttribute("allPatients", allPatients);
		logger.info("recentlyEditedPatientId : "+recentlyEditedPatientId+" --> recentlyAddedPatientId : "+recentlyAddedPatientId);
		if(recentlyAddedPatientId!=null) {
			Patient recentlyAddedPatient = patientRepository.findById(recentlyAddedPatientId).get();
			model.addAttribute("recentlyAddedPatient", recentlyAddedPatient);
			logger.info("Recently added patient model var set "+recentlyAddedPatient);
		}
		if(recentlyEditedPatientId!=null) {
			Patient recentlyEditedPatient = patientRepository.findById(recentlyEditedPatientId).get();
			model.addAttribute("recentlyEditedPatient", recentlyEditedPatient);
			logger.info("Recently edited patient model var set "+recentlyEditedPatient);
		}
		if(recentlyDeletedPatientId != null) {
			model.addAttribute("recentlyDeletedPatientId", recentlyDeletedPatientId);
			model.addAttribute("recentlyDeletedPatientName", recentlyDeletedPatientName);
			logger.info("Recently deleted patient model var set "+recentlyDeletedPatientId+" "+recentlyDeletedPatientName);
		}
		
		return "patients/all_patients";
	}
	
	@GetMapping("/view/{patient_id}")
	public String getPatientById(@PathVariable("patient_id") Integer patientId, Model model) {
		logger.info("Fetching patient id "+patientId);
		Patient patient = patientRepository.findById(patientId).get();
		logger.info("Patient fetched : "+patient);
		model.addAttribute("patient", patient);
		
		logger.info("Fetching treatment records for patient id : "+patientId);
		
		List<Treatment> allTreatments = treatmentRepository.findByPatient(patient);
		logger.info("All treatments for Patient ("+patient.getId()+","+patient.getName()+") : "+allTreatments);
		model.addAttribute("allTreatments", allTreatments);
		
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
		return "redirect:/patients/view_patients?recentlyDeletedPatientId="+patient.getId()+"&recentlyDeletedPatientName="+patient.getName();
	}
	
	@GetMapping("/edit/{patient_id}")
	public String editPatientForm(@PathVariable("patient_id") Integer patientId, Model model) {
		Patient patient = patientRepository.findById(patientId).get();
		model.addAttribute("patient", patient);
		return "patients/edit_patient_form";
	}
	
	@PostMapping("/edit_patient")
	public String editPatient(@ModelAttribute("patient") @Valid Patient patient, BindingResult bindingResult) {
		logger.info("patient came for edit "+patient);
		if(bindingResult.hasErrors()) {
			logger.warn("Patient have invalid values. "+bindingResult);
			return "patients/edit_patient_form";
		}
		
		//This will happen when someone didnt choose any option. So instead of save ",", we save blank
		if(patient.getHowDidYouFindUs().equals(",")) {
			patient.setHowDidYouFindUs("");
		}
		
		//Remove extra comma from the end
		if(patient.getHowDidYouFindUs().endsWith(",")) {
			int lengthOfHowDidYouFindUs=patient.getHowDidYouFindUs().length();
			patient.setHowDidYouFindUs(patient.getHowDidYouFindUs().substring(0, lengthOfHowDidYouFindUs-1));
		}
		
		patientRepository.save(patient);
		logger.info("Patient Saved with patient id "+patient.getId());
		return "redirect:/patients/view_patients?recentlyEditedPatientId="+patient.getId();
	}
}

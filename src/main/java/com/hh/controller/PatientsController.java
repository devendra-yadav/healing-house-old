package com.hh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hh.entity.Patient;
import com.hh.entity.Treatment;
import com.hh.repository.PatientRepository;
import com.hh.repository.TreatmentRepository;
import com.hh.service.PatientService;
import com.hh.service.TreatmentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/patients")
public class PatientsController {

	private Logger logger = LoggerFactory.getLogger(PatientsController.class);
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private TreatmentService treatmentService;
	
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
		
		Patient patient = patientService.savePatient(patientDto);
		
		logger.info("Saved New Patient. Patient ID : "+patient.getId());
		return "redirect:/patients/view_patients?recentlyAddedPatientId="+patient.getId();
	}
	
	@GetMapping("/view_patients")
	public String viewPatients(@RequestParam(name = "recentlyAddedPatientId", required=false) Integer recentlyAddedPatientId , @RequestParam(name = "recentlyEditedPatientId", required=false) Integer recentlyEditedPatientId, @RequestParam(name = "recentlyDeletedPatientId", required=false) Integer recentlyDeletedPatientId, @RequestParam(name = "recentlyDeletedPatientName", required=false) String recentlyDeletedPatientName, Model model) {
		List<Patient> allPatients = patientService.getAllPatients();
		model.addAttribute("allPatients", allPatients);
		logger.info("recentlyEditedPatientId : "+recentlyEditedPatientId+" --> recentlyAddedPatientId : "+recentlyAddedPatientId+" --> recentlyDeletedPatientId : "+recentlyDeletedPatientId);
		if(recentlyAddedPatientId!=null) {
			Patient recentlyAddedPatient = patientService.getPatientById(recentlyAddedPatientId);
			model.addAttribute("recentlyAddedPatient", recentlyAddedPatient);
			logger.info("Recently added patient model var set "+recentlyAddedPatient);
		}
		if(recentlyEditedPatientId!=null) {
			Patient recentlyEditedPatient = patientService.getPatientById(recentlyEditedPatientId);
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
		Patient patient = patientService.getPatientById(patientId);
		logger.info("Patient fetched : "+patient);
		model.addAttribute("patient", patient);
		
		logger.info("Fetching treatment records for patient id : "+patientId);
		
		List<Treatment> allTreatments = treatmentService.getAllTreatmentsForPatient(patient);
		
		//Create a map of treatmentId and corresponding total payment done.
		Map<Integer, Integer> treatmentPaymentMap = new HashMap<>();
		for(Treatment treatment: allTreatments) {
			Integer totalPaymentDoneForTreatment = treatment.getPayments().stream().map((payment)->{
				return payment.getPaymentAmount();
			}).reduce(0,(a,b)->a+b);
			treatmentPaymentMap.put(treatment.getId(), totalPaymentDoneForTreatment);
		}
		
		logger.info("All treatments for Patient ("+patient.getId()+","+patient.getName()+") : "+allTreatments);
		model.addAttribute("allTreatments", allTreatments);
		model.addAttribute("treatmentPaymentMap", treatmentPaymentMap);
		
		return "/patients/patient_details";
	}
	
	@GetMapping("/delete/{patient_id}")
	public String deletePackage(@PathVariable("patient_id") Integer patientId,  Model model) {
		Patient patient = patientService.getPatientById(patientId);
		if(patient!=null) {
			patientService.deletePatient(patient);
			logger.info("Patient '"+patient.getName()+"' deleted.");
		}else {
			logger.warn("Invalid patient id "+patientId+". Cant delete!");
		}
		return "redirect:/patients/view_patients?recentlyDeletedPatientId="+patient.getId()+"&recentlyDeletedPatientName="+patient.getName();
	}
	
	@GetMapping("/edit/{patient_id}")
	public String editPatientForm(@PathVariable("patient_id") Integer patientId, Model model) {
		Patient patient = patientService.getPatientById(patientId);
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
		
		patient = patientService.savePatient(patient);
		
		logger.info("Patient Saved with patient id "+patient.getId());
		return "redirect:/patients/view_patients?recentlyEditedPatientId="+patient.getId();
	}
}

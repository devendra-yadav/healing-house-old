package com.hh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hh.dto.TreatmentDTO;
import com.hh.entity.Package;
import com.hh.entity.Patient;
import com.hh.entity.Payment;
import com.hh.entity.Treatment;
import com.hh.repository.PackageRepository;
import com.hh.repository.PatientRepository;
import com.hh.repository.PaymentRepository;
import com.hh.repository.TreatmentRepository;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/treatment")
public class TreatmentController {
	
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	private Logger logger = LoggerFactory.getLogger(TreatmentController.class);
	
	@GetMapping("/select_treatment_form/{patient_id}")
	public String selectTreatmentForm(@PathVariable("patient_id") Integer patientId, Model model) {
		
		Patient patient = patientRepository.findById(patientId).get();
		
		logger.info("Request came to select treatment for patient "+patient);
		List<Package> allPackages = packageRepository.findAll();
		TreatmentDTO treatmentDto = new TreatmentDTO();
		treatmentDto.setPatientId(patientId);
		
		Map<Integer, Package> packageMap = new HashMap<>();
		allPackages.stream().forEach((pkg)->{
			packageMap.put(pkg.getId(), pkg);
		});
		
		model.addAttribute("all_packages", allPackages);
		model.addAttribute("treatment", treatmentDto);
		model.addAttribute("patient", patient);
		model.addAttribute("packageMap", packageMap);
		
		
		return "treatments/select_treatment_form";
	}
	
	@PostMapping("/select_treatment")
	public String selectTreatment(@ModelAttribute("treatment") TreatmentDTO treatmentDto, Model model) {
		logger.info("treatment selected "+treatmentDto);
		Patient patient = patientRepository.findById(treatmentDto.getPatientId()).get();
		Package pkg = packageRepository.findById(treatmentDto.getPackageId()).get();
		
		Treatment treatment = new Treatment(treatmentDto, patient, pkg);
		treatment = treatmentRepository.save(treatment);
		
		Payment payment = new Payment(treatment, treatment.getAmountPaid(), treatment.getStartDate());
		paymentRepository.save(payment);		
		logger.info("Treatment saved "+treatment);
		
		return "redirect:/patients/view/"+treatmentDto.getPatientId();
	}
	
	@GetMapping("/get_treatment/{treatment_id}")
	public String getTreatment(@PathVariable("treatment_id") Integer treatmentId, Model model ) {
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		logger.info("Treatment Details : "+treatment);
		
		
		return "/home";
	}
	
	@GetMapping("/pay/{patientId}/{treatmentId}")
	public String makePayment(@PathVariable("patientId") Integer patientId, @PathVariable("treatmentId") Integer treatmentId, Model model) {
		Patient patient = patientRepository.findById(patientId).get();
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		
		
		treatment.setFullyPaid(true);
		treatmentRepository.save(treatment);
		logger.info("Payment status for patient "+patient.getName()+" (treatment id : "+treatmentId+") changed to PAID");
		
		List<Treatment> allTreatments = treatmentRepository.findByPatient(patient);
		model.addAttribute("allTreatments", allTreatments);
		model.addAttribute("patient", patient);		
		return "/patients/patient_details";
	}
	
	@GetMapping("/delete/{patientId}/{treatmentId}")
	@Transactional
	public String deleteTreatment(@PathVariable("patientId") Integer patientId, @PathVariable("treatmentId") Integer treatmentId, Model model) {
		Patient patient = patientRepository.findById(patientId).get();
		logger.info("Request came to delete treatment id "+treatmentId+" for patient : "+patient);
		
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		Long paymentRecordsDeleted = paymentRepository.removeByTreatment(treatment);
		logger.info("Total of "+paymentRecordsDeleted+" payment records deleted for treatment :"+treatment);
		treatmentRepository.deleteById(treatmentId);
		logger.info("deleted treatment id "+treatmentId+" for patient : "+patient);
		
		List<Treatment> allTreatments = treatmentRepository.findByPatient(patient);
		model.addAttribute("allTreatments", allTreatments);
		model.addAttribute("patient", patient);		
		return "/patients/patient_details";
	}
	
	@GetMapping("/edit/{patientId}/{treatmentId}")
	public String editTreatmentForm(@PathVariable("patientId") Integer patientId, @PathVariable("treatmentId") Integer treatmentId, Model model) {
		Patient patient = patientRepository.findById(patientId).get();
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		logger.info("Request came to edit treatment "+treatment+" for patient : "+patient);
		List<Package> allPackages = packageRepository.findAll();
		model.addAttribute("patient", patient);		
		model.addAttribute("treatment", treatment);
		model.addAttribute("all_packages", allPackages);
		
		return "/treatments/edit_treatment_form";
	}
	
	@PostMapping("/edit_treatment")
	public String editTreatment(@ModelAttribute("treatment") Treatment treatment, Model model) {
		logger.info("request to update treatment. updated treatment "+treatment);
		treatment = treatmentRepository.save(treatment);
		logger.info("Treatment updated "+treatment);
		
		return "redirect:/patients/view/"+treatment.getPatient().getId();
	}
}

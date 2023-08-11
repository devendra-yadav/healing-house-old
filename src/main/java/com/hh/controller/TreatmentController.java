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
import com.hh.service.PackageService;
import com.hh.service.PatientService;
import com.hh.service.PaymentService;
import com.hh.service.TreatmentService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/treatment")
public class TreatmentController {
	
	@Autowired
	private TreatmentService treatmentService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PaymentService paymentService;
	
	private Logger logger = LoggerFactory.getLogger(TreatmentController.class);
	
	@GetMapping("/select_treatment_form/{patient_id}")
	public String selectTreatmentForm(@PathVariable("patient_id") Integer patientId, Model model) {
		
		Patient patient = patientService.getPatientById(patientId);
		
		logger.info("Request came to select treatment for patient "+patient);
		List<Package> allPackages = packageService.getAllPackages();
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
		Patient patient = patientService.getPatientById(treatmentDto.getPatientId());
		Package pkg = packageService.getPackageById(treatmentDto.getPackageId());
		
		//Treatment treatment = new Treatment(treatmentDto, patient, pkg);
		//treatment = treatmentService.saveTreatment(treatment);
		
		//Payment payment = new Payment(treatment, treatmentDto.getAmountPaid(), treatmentDto.getPaymentMethod(), treatment.getStartDate());
		//paymentService.savePayment(payment);		
		//logger.info("Treatment saved "+treatment);
		
		return "redirect:/patients/view/"+treatmentDto.getPatientId();
	}
	
	@GetMapping("/get_treatment/{treatment_id}")
	public String getTreatment(@PathVariable("treatment_id") Integer treatmentId, Model model ) {
		Treatment treatment = treatmentService.getTreatmentById(treatmentId);
		logger.info("Treatment Details : "+treatment);
		
		return "/home";
	}
	
	
	@GetMapping("/delete/{patientId}/{treatmentId}")
	@Transactional
	public String deleteTreatment(@PathVariable("patientId") Integer patientId, @PathVariable("treatmentId") Integer treatmentId, Model model) {
		Patient patient = patientService.getPatientById(patientId);
		logger.info("Request came to delete treatment id "+treatmentId+" for patient : "+patient);
		
		Treatment treatment = treatmentService.getTreatmentById(treatmentId);
		Long paymentRecordsDeleted = paymentService.deletePaymentByTreatment(treatment);
		logger.info("Total of "+paymentRecordsDeleted+" payment records deleted for treatment :"+treatment);
		treatmentService.deleteTreatmentById(treatmentId);
		logger.info("deleted treatment id "+treatmentId+" for patient : "+patient);
		
		return "redirect:/patients/view/"+patient.getId();
	}
	
	@GetMapping("/edit/{patientId}/{treatmentId}")
	public String editTreatmentForm(@PathVariable("patientId") Integer patientId, @PathVariable("treatmentId") Integer treatmentId, Model model) {
		Patient patient = patientService.getPatientById(patientId);
		Treatment treatment = treatmentService.getTreatmentById(treatmentId);
		logger.info("Request came to edit treatment "+treatment+" for patient : "+patient);
		List<Package> allPackages = packageService.getAllPackages();
		
		int totalAmountPaid = treatment.getPayments().stream().map((pymt)->pymt.getPaymentAmount()).reduce(0, (a,b)->a+b);
		
		model.addAttribute("totalAmountPaid", totalAmountPaid);
		model.addAttribute("patient", patient);		
		model.addAttribute("treatment", treatment);
		model.addAttribute("all_packages", allPackages);
		
		return "treatments/edit_treatment_form";
	}
	
	@PostMapping("/edit_treatment")
	public String editTreatment(@ModelAttribute("treatment") Treatment treatment, Model model) {
		logger.info("request to update treatment. updated treatment "+treatment);
		treatment = treatmentService.saveTreatment(treatment);
		logger.info("Treatment updated "+treatment);
		
		
		return "redirect:/patients/view/"+treatment.getPatient().getId();
	}
}

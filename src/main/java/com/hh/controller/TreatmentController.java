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
import com.hh.entity.Treatment;
import com.hh.repository.PackageRepository;
import com.hh.repository.PatientRepository;
import com.hh.repository.TreatmentRepository;

@Controller
@RequestMapping("/treatment")
public class TreatmentController {
	
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
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
		logger.info("Treatment saved "+treatment);
		
		return "redirect:/patients/view/"+treatmentDto.getPatientId();
	}
	
	@GetMapping("/get_treatment/{treatment_id}")
	public String getTreatment(@PathVariable("treatment_id") Integer treatmentId, Model model ) {
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		logger.info("Treatment Details : "+treatment);
		
		return "/home";
	}
}

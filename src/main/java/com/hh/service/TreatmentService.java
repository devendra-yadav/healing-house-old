package com.hh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.entity.Patient;
import com.hh.entity.Treatment;
import com.hh.repository.TreatmentRepository;

@Service
public class TreatmentService {
	
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	public List<Treatment> getAllTreatmentsForPatient(Patient patient){
		List<Treatment> allTreatments = treatmentRepository.findByPatient(patient);
		
		return allTreatments;
	}
	
	public Treatment getTreatmentById(int treatmentId) {
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		return treatment;
	}
	
	public Treatment saveTreatment(Treatment treatment) {
		treatment = treatmentRepository.save(treatment);
		return treatment;
	}
	
	public void deleteTreatmentById(int treatmentId) {
		treatmentRepository.deleteById(treatmentId);
	}
	
}

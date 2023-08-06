package com.hh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.dto.PatientDTO;
import com.hh.entity.Patient;
import com.hh.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public Patient savePatient(PatientDTO patientDto) {
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
		
		return patient;
	}
	
	public Patient savePatient(Patient patient) {
		//This will happen when someone didnt choose any option. So instead of save ",", we save blank
		if(patient.getHowDidYouFindUs().equals(",")) {
			patient.setHowDidYouFindUs("");
		}
		
		//Remove extra comma from the end
		while(patient.getHowDidYouFindUs().endsWith(",")) {
			int lengthOfHowDidYouFindUs=patient.getHowDidYouFindUs().length();
			patient.setHowDidYouFindUs(patient.getHowDidYouFindUs().substring(0, lengthOfHowDidYouFindUs-1));
		}
		
		//Remove extra comma from the beginning
		while(patient.getHowDidYouFindUs().startsWith(",")) {
			int lengthOfHowDidYouFindUs=patient.getHowDidYouFindUs().length();
			patient.setHowDidYouFindUs(patient.getHowDidYouFindUs().substring(1, lengthOfHowDidYouFindUs));
		}
		
		patient = patientRepository.save(patient);
		
		return patient;
	}
	
	public Patient getPatientById(int patientId) {
		Patient patient = patientRepository.findById(patientId).get();
		return patient;
	}
	
	public List<Patient> getAllPatients(){
		List<Patient> allPatients = patientRepository.findAll();
		return allPatients;
	}
	
	public void deletePatient(Patient patient) {
		patientRepository.delete(patient);
	}
	
}

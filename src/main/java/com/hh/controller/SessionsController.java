package com.hh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.dto.SessionDTO;
import com.hh.entity.Session;
import com.hh.entity.Treatment;
import com.hh.repository.SessionRepository;
import com.hh.repository.TreatmentRepository;

@Controller
@RequestMapping("/sessions")
public class SessionsController {
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	@Autowired
	private SessionRepository sessionRepository;
	
	private Logger logger = LoggerFactory.getLogger(SessionsController.class);

	@GetMapping("/view/treatment/{treatment_id}")
	public String viewTreatmentAllSessions(@PathVariable("treatment_id") Integer treatmentId, Model model) {
		
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		List<Session> allSessionsForTreatment = sessionRepository.findByTreatment(treatment);
		logger.info("Got all sessions for treatment id ("+treatmentId+") : "+allSessionsForTreatment);
		
		model.addAttribute("allSessionsForTreatment", allSessionsForTreatment);
		model.addAttribute("treatment", treatment);
		
		
		return "sessions/all_sessions_specific_treatment";
	}
	
	@GetMapping("/new_session_form/{treatment_id}")
	public String newSessionForm(@PathVariable("treatment_id") Integer treatmentId, Model model) {
		
		SessionDTO sessionDto = new SessionDTO();
		
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		model.addAttribute("treatment", treatment);
		model.addAttribute("sessionDto", sessionDto);
		
		return "sessions/new_session_form";
	}
	
}

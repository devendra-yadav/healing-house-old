package com.hh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sessions")
public class SessionsController {

	@GetMapping("/view/treatment/{treatment_id}")
	public String viewTreatmentSessions(@PathVariable("treatment_id") Integer treatmentId, Model model) {
		
		return "/";
	}
	
}

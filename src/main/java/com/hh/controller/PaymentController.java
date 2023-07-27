package com.hh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.entity.Treatment;
import com.hh.repository.PaymentRepository;
import com.hh.repository.TreatmentRepository;

@Controller
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private TreatmentRepository treatmentRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;

	Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@GetMapping("/treatment/{treatment_id}")
	public String viewPaymentsForTreatment(@PathVariable("treatment_id") Integer treatmentId, Model model) {
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		logger.info("fetched treatment for id +"+treatmentId+" : "+treatment);
		
		model.addAttribute("treatment", treatment);
		return "payments/payment_details";
		
	}
}

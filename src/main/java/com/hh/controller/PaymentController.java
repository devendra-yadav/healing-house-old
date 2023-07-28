package com.hh.controller;

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

import com.hh.dto.PaymentDTO;
import com.hh.entity.Payment;
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
		
		int totalPaymentDone = treatment.getPayments().stream().map((payment->payment.getPaymentAmount())).reduce(0, (a,b)->a+b);
		int pendingAmount = treatment.getPrice() - totalPaymentDone;
		
		model.addAttribute("treatment", treatment);
		model.addAttribute("totalPaymentDone", totalPaymentDone);
		model.addAttribute("pendingAmount", pendingAmount);
		
		return "payments/payment_details";
	}
	
	@GetMapping("/treatment/{treatment_id}/payment_form")
	public String treatmentPaymentForm(@PathVariable("treatment_id") Integer treatmentId, Model model) {
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		logger.info("fetched treatment for id +"+treatmentId+" : "+treatment);
		
		int totalPaymentDone = treatment.getPayments().stream().map((payment->payment.getPaymentAmount())).reduce(0, (a,b)->a+b);
		int pendingAmount = treatment.getPrice() - totalPaymentDone;
		
		PaymentDTO paymentDto = new PaymentDTO();
		paymentDto.setPaymentAmount(pendingAmount);
		
		model.addAttribute("treatment", treatment);
		model.addAttribute("totalPaymentDone", totalPaymentDone);
		model.addAttribute("pendingAmount", pendingAmount);
		model.addAttribute("paymentDto", paymentDto);
		
		return "payments/payment_form";
	}
	
	@PostMapping("/makePayment/{treatment_id}")
	public String makePayment(@PathVariable("treatment_id") Integer treatmentId, @ModelAttribute("paymentDto") PaymentDTO paymentDto, Model model) {
		logger.info("Request came to make payment for treatment id "+treatmentId+". "+paymentDto);
		
		Treatment treatment = treatmentRepository.findById(treatmentId).get();
		Payment payment = new Payment(treatment, paymentDto.getPaymentAmount(), paymentDto.getPaymentMethod(), paymentDto.getPaymentDate());
		paymentRepository.save(payment);
		
		
		return "redirect:/payments/treatment/"+treatmentId;
	}
}

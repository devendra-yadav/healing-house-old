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
		
		//Check if all payments done.
		int totalPaymentDone = treatment.getPayments().stream().map((pymt)->pymt.getPaymentAmount()).reduce(0, (a,b)->a+b);
		int totalTreatmentPrice = treatment.getPrice();
		
		if(totalPaymentDone == totalTreatmentPrice) {
			treatment.setFullyPaid(true);
			treatmentRepository.save(treatment);
			logger.info("Full payment done for teatment : "+treatment);
		}else if(totalPaymentDone < totalTreatmentPrice && treatment.isFullyPaid() == true){
			treatment.setFullyPaid(false);
			treatmentRepository.save(treatment);
			logger.info("Full payment status changed to 'NOT FULLY PAID' for teatment : "+treatment);
		}
		
		return "redirect:/payments/treatment/"+treatmentId;
	}
	
	@GetMapping("/delete/{payment_id}")
	public String deletePayment(@PathVariable("payment_id") Integer paymentId, Model model) {
		Payment payment = paymentRepository.findById(paymentId).get();
		
		//Check if all payments done.
		int totalPaymentDone = payment.getTreatment().getPayments().stream().map((pymt)->pymt.getPaymentAmount()).reduce(0, (a,b)->a+b);
		int totalTreatmentPrice = payment.getTreatment().getPrice();
		
		if(totalPaymentDone < totalTreatmentPrice && payment.getTreatment().isFullyPaid() == true) {
			Treatment treatment = payment.getTreatment();
			treatment.setFullyPaid(false);
			treatmentRepository.save(treatment);
			logger.info("Full payment status changed from 'DONE' to 'NOT DONE' for teatment : "+treatment);
		}
		
		paymentRepository.deleteById(paymentId);
		logger.info("deleted payment : "+payment);
		
		return "redirect:/payments/treatment/"+payment.getTreatment().getId();
	}
	
	@GetMapping("/edit_payment_form/{payment_id}")
	public String editPaymentForm(@PathVariable("payment_id") Integer paymentId, Model model) {
		
		Payment payment = paymentRepository.findById(paymentId).get();
		
		logger.info("Request came to edit payment : "+payment);
		int totalPaymentDone = payment.getTreatment().getPayments().stream().map((pymt->pymt.getPaymentAmount())).reduce(0, (a,b)->a+b);
		int pendingAmount = payment.getTreatment().getPrice() - totalPaymentDone;
		
		
		model.addAttribute("totalPaymentDone", totalPaymentDone);
		model.addAttribute("pendingAmount", pendingAmount);
		model.addAttribute("payment", payment);
		model.addAttribute("treatment", payment.getTreatment());
		
		
		return "payments/edit_payment_form";
	}
	
	@PostMapping("/edit_payment")
	public String editPayment(@ModelAttribute("payment") Payment payment, Model model) {
		logger.info("Edited payment : "+payment);
		paymentRepository.save(payment);
		
		int totalTreatmentPrice = payment.getTreatment().getPrice();
		int totalPaymentDone = payment.getTreatment().getPayments().stream().map((pymt->pymt.getPaymentAmount())).reduce(0, (a,b)->a+b);
		
		if(totalPaymentDone < totalTreatmentPrice && payment.getTreatment().isFullyPaid() == true) {
			Treatment treatment = payment.getTreatment();
			treatment.setFullyPaid(false);
			treatmentRepository.save(treatment);
			logger.info("Full payment status changed from 'DONE' to 'NOT DONE' for teatment : "+treatment);
		}
		
		logger.info("Edited payment : "+payment);
		
		
		return "redirect:/payments/treatment/"+payment.getTreatment().getId();
	}
	
}

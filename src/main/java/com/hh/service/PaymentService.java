package com.hh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.dto.PaymentDTO;
import com.hh.entity.Payment;
import com.hh.entity.Treatment;
import com.hh.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	public Payment savePayment(Treatment treatment, PaymentDTO paymentDto) {
		
		Payment payment = new Payment(treatment, paymentDto.getPaymentAmount(), paymentDto.getPaymentMethod(), paymentDto.getPaymentDate());
		payment = paymentRepository.save(payment);
		return payment;
	}
	
	public Payment savePayment(Payment payment) {
		payment = paymentRepository.save(payment);
		return payment;
	}
	
	public Payment getPaymentById(int paymentId) {
		Payment payment = paymentRepository.findById(paymentId).get();
		return payment;
	}
	
	public void deletePaymentById(int paymentId) {
		paymentRepository.deleteById(paymentId);
	}
	
	public long deletePaymentByTreatment(Treatment treatment) {
		Long paymentRecordsDeleted = paymentRepository.removeByTreatment(treatment);
		return paymentRecordsDeleted;
	}
}

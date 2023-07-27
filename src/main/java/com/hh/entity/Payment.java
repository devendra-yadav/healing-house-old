package com.hh.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Treatment treatment;
	private Integer paymentAmount;
	private String paymentMethod;
	private LocalDateTime paymentDate;
	
	public Payment(Treatment treatment, Integer paymentAmount, LocalDateTime paymentDate) {
		this.treatment = treatment;
		this.paymentAmount = paymentAmount;
		this.paymentDate = paymentDate;
	}
	
}

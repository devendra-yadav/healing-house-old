package com.hh.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id",insertable=false, updatable=false)
	private Treatment treatment;
	private Integer paymentAmount;
	private String paymentMethod;
	private LocalDateTime paymentDate;
	
	public Payment(Treatment treatment, Integer paymentAmount, String paymentMethod, LocalDateTime paymentDate) {
		this.treatment = treatment;
		this.paymentAmount = paymentAmount;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", treatment=" + treatment.getId() + ", paymentAmount=" + paymentAmount
				+ ", paymentMethod=" + paymentMethod + ", paymentDate=" + paymentDate + "]";
	}
	
}

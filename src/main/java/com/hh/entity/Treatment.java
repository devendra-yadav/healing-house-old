package com.hh.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.hh.dto.TreatmentDTO;

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
public class Treatment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Patient patient;
	@ManyToOne
	private Package pkg;
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime startDate = LocalDateTime.now();
	private int price;
	private Integer amountPaid;
	private String initialPaymentMethod;
	private int totalSessions;
	private int completedSessions;
	private boolean fullyPaid;
	
	
	public Treatment(TreatmentDTO treatmentDto, Patient patient, Package pkg) {
		this.patient = patient;
		this.pkg = pkg;
		this.price = treatmentDto.getPrice();
		this.amountPaid = treatmentDto.getAmountPaid();
		this.initialPaymentMethod = treatmentDto.getInitialPaymentMethod();
		this.totalSessions = treatmentDto.getTotalSessions();
		this.completedSessions = 0;
		if(amountPaid == price) {
			this.fullyPaid = true;
		}else {
			this.fullyPaid = treatmentDto.isFullyPaid();
		}
		
	}
}

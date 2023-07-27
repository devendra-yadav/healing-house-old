package com.hh.dto;

import java.time.LocalDateTime;

import com.hh.entity.Patient;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO {

	private Integer patientId;
	private Integer packageId;
	private LocalDateTime date;
	private Integer price;
	private Integer amountPaid;
	private String initialPaymentMethod;
	@Min(0)
	private int totalSessions;
	private int completedSessions;
	private boolean fullyPaid;
	
	
}

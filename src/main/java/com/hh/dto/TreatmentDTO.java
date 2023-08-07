package com.hh.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDTO {

	private String name;
	private Integer patientId;
	private Integer packageId;
	private LocalDateTime startDate;
	private Integer price;
	private Integer amountPaid;
	private String paymentMethod;
	@Min(0)
	private int totalSessions;
	private int completedSessions;
	private boolean fullyPaid;
	
	
	//Initial Enquiry
	private String complexion;
	private String eyes;
	private String stateOfMind;
	
	private String leftCheekLiver;
	private String chinKidney;
	private String nodeSpleen;
	private String rightCheekLung;
	
	private String darkSpots;
	
	
	
}

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
	@Min(0)
	private int totalSessions;
	private int completedSessions;
	private boolean hasPaid;
	private String initialDiagnosis;
	
}

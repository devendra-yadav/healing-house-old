package com.hh.entity;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Session {
	
	
	private String leftPulseSI;
	private String leftPulseHT;
	private String leftPulseGB;
	private String leftPulseLIV;
	private String leftPulseUB;
	private String leftPulseKDL;
	
	private String rightPulseLI;
	private String rightPulseLU;
	private String rightPulseST;
	private String rightPulseSP;
	private String rightPulseTW_KDR;
	private String rightPulsePC;
	
	private String diagnosis;
	private String acupoints;
	
	@ManyToOne
	private Treatment treatment;
}

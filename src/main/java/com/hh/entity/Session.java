package com.hh.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.hh.dto.SessionDTO;

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
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int sessionNumber;
	
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
	
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime sessionDate = LocalDateTime.now();;
	
	@ManyToOne
	private Treatment treatment;
	
	public Session(SessionDTO sessionDto, Treatment treatment) {
		this.sessionNumber = sessionDto.getSessionNumber();
		
		this.leftPulseSI = sessionDto.getLeftPulseSI();
		this.leftPulseHT = sessionDto.getLeftPulseHT();
		this.leftPulseGB = sessionDto.getLeftPulseGB();
		this.leftPulseLIV = sessionDto.getLeftPulseLIV();
		this.leftPulseUB = sessionDto.getLeftPulseUB();
		this.leftPulseKDL = sessionDto.getLeftPulseKDL();
		
		this.rightPulseLI = sessionDto.getRightPulseLI();
		this.rightPulseLU = sessionDto.getRightPulseLU();
		this.rightPulseST = sessionDto.getRightPulseST();
		this.rightPulseSP = sessionDto.getRightPulseSP();
		this.rightPulseTW_KDR = sessionDto.getRightPulseTW_KDR();
		this.rightPulsePC = sessionDto.getRightPulsePC();
		
		this.diagnosis = sessionDto.getDiagnosis();
		this.acupoints = sessionDto.getAcupoints();
		
		this.treatment = treatment;
		
	}
	
}

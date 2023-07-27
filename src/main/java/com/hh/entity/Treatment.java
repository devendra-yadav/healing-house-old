package com.hh.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.hh.dto.TreatmentDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
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
	private Integer price;
	
	@OneToMany(mappedBy = "treatment")
	List<Payment> payments;
	private int totalSessions;
	private int completedSessions;
	private boolean fullyPaid;
	
	
	public Treatment(TreatmentDTO treatmentDto, Patient patient, Package pkg) {
		this.patient = patient;
		this.pkg = pkg;
		this.price = treatmentDto.getPrice();
		this.totalSessions = treatmentDto.getTotalSessions();
		this.completedSessions = 0;
		this.fullyPaid = treatmentDto.isFullyPaid();
		
	}


	@Override
	public String toString() {
		return "Treatment [id=" + id + ", patient=" + patient + ", pkg=" + pkg + ", startDate=" + startDate + ", price="
				+ price + ", payments=" + payments + ", totalSessions=" + totalSessions + ", completedSessions="
				+ completedSessions + ", fullyPaid=" + fullyPaid + "]";
	}
	
	
}

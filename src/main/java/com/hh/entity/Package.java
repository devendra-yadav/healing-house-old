package com.hh.entity;

import com.hh.dto.PackageDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Package {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Package name cant be blank")
	private String name;
	
	@NotNull(message = "Invalid number")
	@Min(value = 0, message = "Invalid Number.")
	@Max(value = 999999999, message = "Invalid Number.")
	private Integer numOfSessions;
	
	@NotNull(message = "Invalid Amount.")
	@Min(value = 0, message = "Invalid Amount.")
	@Max(value = 999999999, message = "Invalid Amount.")
	private Integer totalFees;
	private String remarks;
	
	public Package(PackageDTO packageDto) {
		this.name = packageDto.getName();
		this.numOfSessions = packageDto.getNumOfSessions();
		this.totalFees = packageDto.getTotalFees();
		this.remarks = packageDto.getRemarks();
	}
	
}

package com.hh.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PackageDTO {

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
	
}

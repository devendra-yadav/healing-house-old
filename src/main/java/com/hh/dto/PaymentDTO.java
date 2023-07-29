package com.hh.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class PaymentDTO {
	private Integer treatmentId;
	private Integer paymentAmount;
	private String paymentMethod;
	private LocalDateTime paymentDate = LocalDateTime.now();

}

package com.Nest.Icu.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistoryDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int SLNO;
	private int heartbeat;
	private int SystolePressure;
	private int diastolepressure;
	private Date date;

}

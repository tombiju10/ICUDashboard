package com.Nest.Icu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAlertHistory {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Slno")
	private int SLNO;
	private int heartbeat;
	private int SystolePressure;
	private int diastolepressure;
	private String date;
    
}

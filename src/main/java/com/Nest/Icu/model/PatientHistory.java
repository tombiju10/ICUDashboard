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
public class PatientHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Slno")
	private int SLNO;
	private int heartbeat;
	private int SystolePressure;
	private int diastolepressure;
	private String date;
	
	
	
	public int getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(int heartbeat) {
		this.heartbeat = heartbeat;
	}
	public int getSystolePressure() {
		return SystolePressure;
	}
	public void setSystolePressure(int systolePressure) {
		SystolePressure = systolePressure;
	}
	public int getDiastolepressure() {
		return diastolepressure;
	}
	public void setDiastolepressure(int diastolepressure) {
		this.diastolepressure = diastolepressure;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSLNO() {
		return SLNO;
	}
	

}

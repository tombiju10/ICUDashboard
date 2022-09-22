package com.Nest.Icu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "patient_id")
	private UUID patientid;

	private String name;
	
	private int age;
	
	private String gender;
	
	@Enumerated(EnumType.STRING)
	private PatientStatus patientStatus;
	
	@OneToMany(targetEntity = PatientHistory.class,cascade = CascadeType.ALL)
	@JoinColumn(name="patient_Fk",referencedColumnName = "patient_id")
	private List<PatientHistory> patientHistory;

	@OneToMany(targetEntity = PatientAlertHistory.class,cascade = CascadeType.ALL)
	@JoinColumn(name="patient_Fk",referencedColumnName = "patient_id")
	private List<PatientAlertHistory> patientAlertHistoryHistories;
		
	
	
	
	public void setPatientHistory(List<PatientHistory> patientHistory) {
		this.patientHistory = patientHistory;
	}

	public void setPatientAlertHistoryHistories(List<PatientAlertHistory> patientAlertHistoryHistories) {
		this.patientAlertHistoryHistories = patientAlertHistoryHistories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public PatientStatus getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(PatientStatus patientStatus) {
		this.patientStatus = patientStatus;
	}

	public UUID getPatientid() {
		return patientid;
	}

	public List<PatientHistory> getPatientHistory() {
		return patientHistory;
	}

	public List<PatientAlertHistory> getPatientAlertHistoryHistories() {
		return patientAlertHistoryHistories;
	}
}

package com.Nest.Icu.controller.dto;

import com.Nest.Icu.model.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID patientid;
	
	private String name;
	
	private int age;
	
	private String gender;
	
	private PatientStatus patientStatus;
	

	
	public UUID getPatientid() {
		return patientid;
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

}

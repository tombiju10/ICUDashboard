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
public class Bed {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bed_id")
	private UUID bedId;

	@Column(name="BedName")
	private String name;
	
	private String heartBeatId;

	private String bloodPressureId;
	
	@Enumerated(EnumType.STRING)
	private BedStatus bedStatus;
	
	@OneToMany(targetEntity = Patient.class,cascade = CascadeType.ALL)
	@JoinColumn(name="bed_Fk",referencedColumnName = "bed_id")
	private List<Patient> patient;
	
	
	
	
	public List<Patient> getPatient() {
		return patient;
	}
	
	public void setPatient(List<Patient> patient) {
		this.patient = patient;
	}

	public UUID getBedId() {
		return bedId;
	}

	public String getHeartBeatId() {
		return heartBeatId;
	}
	
	public void setHeartBeatId(String heartBeatId) {
		this.heartBeatId = heartBeatId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBloodPressureId() {
		return bloodPressureId;
	}

	public void setBloodPressureId(String bloodPressureId) {
		this.bloodPressureId = bloodPressureId;
	}

	public BedStatus getBedStatus() {
		return bedStatus;
	}

	public void setBedStatus(BedStatus bedStatus) {
		this.bedStatus = bedStatus;
	}


}

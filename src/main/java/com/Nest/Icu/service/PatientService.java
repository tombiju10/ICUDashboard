package com.Nest.Icu.service;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.exceptions.PatientAlreadyExistsException;
import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.model.Patient;
import com.Nest.Icu.model.PatientHistory;

import java.util.List;
import java.util.UUID;


public interface PatientService {
	
	public Patient addPatient(PatientDto patientDto, UUID bedid) 
			throws BedNotFoundException, PatientAlreadyExistsException;
	
	public Patient viewPatient(UUID patientId) throws PatientNotFoundException;
	
	public List<Patient> getPatientList();
	
	public Patient updatePatient(PatientDto patientDto, UUID patientId) throws PatientNotFoundException;
	
	public void deletePatient(UUID patientId) throws PatientNotFoundException;

	public List<PatientHistory> getPatientHistory(UUID patientId) throws PatientNotFoundException;

	public PatientHistory getLastData(UUID patientId) throws  PatientNotFoundException;
}

package com.Nest.Icu.service;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.exceptions.BedAlreadyExistsException;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.exceptions.PatientAlreadyExistsException;
import com.Nest.Icu.model.Bed;
import com.Nest.Icu.model.Patient;

import java.util.List;
import java.util.UUID;

public interface BedService {
	
	public Bed addBed(Bed bed) throws BedAlreadyExistsException;
	
	public List<Bed> getBedsList();
	
	public Bed updateBed(Bed bed, UUID uuid) throws BedNotFoundException;

	public void deleteBed(UUID uuid) throws BedNotFoundException;
	
	public Bed getBedData(UUID uuid) throws BedNotFoundException;
	
	public Patient addPatient(PatientDto patientDto, UUID bedId)
			throws BedNotFoundException, PatientAlreadyExistsException;

}

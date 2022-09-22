package com.Nest.Icu.service.impl;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.exceptions.BedAlreadyExistsException;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.exceptions.PatientAlreadyExistsException;
import com.Nest.Icu.mapper.PatientMapper;
import com.Nest.Icu.model.Bed;
import com.Nest.Icu.model.Patient;
import com.Nest.Icu.model.PatientStatus;
import com.Nest.Icu.repository.BedRepository;
import com.Nest.Icu.repository.PatientRepository;
import com.Nest.Icu.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BedServiceImpl implements BedService{
	
	@Autowired
	private BedRepository bedRepository;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Bed addBed(Bed bed) throws BedAlreadyExistsException {
		return bedRepository.save(bed);
	}

	@Override
	public List<Bed> getBedsList() {
		
		
		return (List<Bed>)bedRepository.findAll();
	}

	@Override
	public Bed updateBed(Bed bed, UUID uuid) throws BedNotFoundException{
		if(!bedRepository.existsById(uuid)) {
			throw new BedNotFoundException("Bed with that id Not present");
		}

		Bed b = bedRepository.getById(uuid);
			b.setName(bed.getName());
			b.setHeartBeatId(bed.getHeartBeatId());
			b.setBloodPressureId(bed.getBloodPressureId());
			b.setBedStatus(bed.getBedStatus());
		return bedRepository.save(b);
	}

	@Override
	public void deleteBed(UUID uuid) throws BedNotFoundException {
		if(!bedRepository.existsById(uuid)) {
			throw new BedNotFoundException("Bed with that id Not present");
		}
		bedRepository.deleteById(uuid);
	}


	@Override
	public Bed getBedData(UUID uuid) throws BedNotFoundException {
	
		if(!bedRepository.existsById(uuid)) {
			throw new BedNotFoundException("Bed with that id Not present");
		}
		return bedRepository.findById(uuid).get();
	}
	
	@Override
	public Patient addPatient(PatientDto patientDto, UUID bedId)
			throws BedNotFoundException, PatientAlreadyExistsException {
		Patient patient = new Patient();
		
		UUID dtoId = patientDto.getPatientid();
		if(dtoId !=null) {
			if(patientRepository.existsById(dtoId)){
				throw new PatientAlreadyExistsException("Patient with that Id already Exists");
			}
		}
		if(!bedRepository.existsById(bedId)) {
			throw new BedNotFoundException("Bed with that ID not found");
		}
		
		//patient=patientMapper.UpdatePatient(patientDto,patient);
		patient = patientMapper.addThePatient(patientDto,patient);
		patient.setPatientStatus(PatientStatus.OnBed);
		Bed bed = bedRepository.findById(bedId).get();
		bed.getPatient().add(patient);
		bedRepository.save(bed);
		return bed.getPatient().get(bed.getPatient().size()-1);

		
		
		
	}
	
	


	
	


}

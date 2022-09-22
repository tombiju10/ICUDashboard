package com.Nest.Icu.service.impl;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.exceptions.PatientAlreadyExistsException;
import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.mapper.PatientMapper;
import com.Nest.Icu.model.Bed;
import com.Nest.Icu.model.Patient;
import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.model.PatientStatus;
import com.Nest.Icu.repository.BedRepository;
import com.Nest.Icu.repository.PatientRepository;
import com.Nest.Icu.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PatientServiceImpl implements PatientService {
	
	@Autowired
	private BedRepository bedRepository;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public Patient addPatient(PatientDto patientDto, UUID bedid)
			throws BedNotFoundException, PatientAlreadyExistsException {
		Patient patient = new Patient();
		
		UUID dtoId = patientDto.getPatientid();
		if(dtoId !=null) {
			if(patientRepository.existsById(dtoId)){
				throw new PatientAlreadyExistsException("Patient with that Id already Exists");
			}
		}
		if(!bedRepository.existsById(bedid)) {
			throw new BedNotFoundException("Bed with that ID not found");
		}
		
		//patient=patientMapper.UpdatePatient(patientDto,patient);
		patient = patientMapper.addThePatient(patientDto,patient);
		patient.setPatientStatus(PatientStatus.OnBed);
		Bed bed = bedRepository.findById(bedid).get();
		bed.getPatient().add(patient);
		bedRepository.save(bed);
		return bed.getPatient().get(bed.getPatient().size()-1);

	}

	@Override
	public Patient viewPatient(UUID patientId) throws PatientNotFoundException {
		
		if(!patientRepository.existsById(patientId)) {
			throw new PatientNotFoundException("Patient with that id Not present");
		}
		return patientRepository.findById(patientId).get();
	}

	@Override
	public List<Patient> getPatientList(){
		
		return (List<Patient>)patientRepository.findAll();
	}

	@Override
	public Patient updatePatient(PatientDto patientDto, UUID patientId) throws PatientNotFoundException {
		
		if(!patientRepository.existsById(patientId)) {
			throw new PatientNotFoundException("Patient with that id Not present");
		}
		
		Patient p = patientRepository.getById(patientId);
		p.setName(patientDto.getName());
		p.setAge(patientDto.getAge());
		p.setGender(patientDto.getGender());
		p.setPatientStatus(patientDto.getPatientStatus());
	return patientRepository.save(p);
	}

	@Override
	public void deletePatient(UUID patientId) throws PatientNotFoundException {
		
		if(!patientRepository.existsById(patientId)) {
			throw new PatientNotFoundException("Patient with that id Not present");
		}
		patientRepository.deleteById(patientId);
	}


	@Override
	public List<PatientHistory> getPatientHistory(UUID patientId) throws PatientNotFoundException {

		if(!patientRepository.existsById(patientId)){
			throw new PatientNotFoundException("patient Not Found with that id");
		}

		Patient patient = patientRepository.findById(patientId).get();

		return patient.getPatientHistory();
	}


	@Override
	public PatientHistory getLastData(UUID patientId) throws PatientNotFoundException {
		if(!patientRepository.existsById(patientId)){
			throw new PatientNotFoundException("patient Not Found with that id");
		}
		Patient patient = patientRepository.findById(patientId).get();

		return patient.getPatientHistory().get(patient.getPatientHistory().size()-2);
	}


}

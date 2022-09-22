package com.Nest.Icu.controller;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.exceptions.PatientAlreadyExistsException;
import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.model.Patient;
import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.service.DeviceService;
import com.Nest.Icu.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/icu/bed/patient")
@CrossOrigin(origins = "*")
public class PatientController {
	
	@Autowired
	private PatientService patientService;

	@Autowired
	private DeviceService deviceService;
	

	@Operation(summary="Add Patient")
	@PostMapping("/add/{bedid}")
	public ResponseEntity<?> postPatient(@RequestBody PatientDto patientDto,@PathVariable(value="bedid")UUID bedid){
		
		ResponseEntity<?> response;
		
		try {
			response = new ResponseEntity<>(patientService.addPatient(patientDto, bedid),HttpStatus.OK);
		}catch(BedNotFoundException | PatientAlreadyExistsException e) {
			response = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
			
		return response;
		
	}
	
	
	@Operation(summary="Get Patient details")
	@GetMapping("/{patientId}")
	public ResponseEntity<?> getPatient(@PathVariable(value = "patientId") UUID patientId)
			throws PatientNotFoundException {

		return new ResponseEntity<Patient>(patientService.viewPatient(patientId),
				HttpStatus.OK);

	}

	
	@Operation(summary="Get Patient List")
	@GetMapping("/list/patient")
	public List<Patient> getPatientList(){
		return patientService.getPatientList();
	}
	
	

	@Operation(summary="Update Patient")
	@PutMapping("/{patientId}")
	public ResponseEntity<?> updatePatient(@RequestBody PatientDto patientDto,@PathVariable("patientId") UUID patientId) throws PatientNotFoundException  {
		
		ResponseEntity<?> response;

		try {
			response = new ResponseEntity<>(patientService.updatePatient(patientDto, patientId), HttpStatus.OK);

		} catch (PatientNotFoundException e) {

			response = new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}

		return response;
	}
		
	
	@Operation(summary="Delete Patient")
	@DeleteMapping("/delete/{patientId}")
	public String deletePatient(@PathVariable ("patientId") UUID patientId) throws PatientNotFoundException{
		patientService.deletePatient(patientId);
		return "deleted";
	}
	
	
	
	/* @Operation(summary="Add Patient History")
	@PostMapping("/data/{patientid}")
	public ResponseEntity<?> post(@RequestBody PatientHistory patientHistory,@RequestParam(value="patientId")UUID patientid) throws PatientNotFoundException{
		
		ResponseEntity<?> response;
		
		try {
			response = new ResponseEntity<>(deviceService.addHistory(patientHistory, patientid),HttpStatus.OK);
		}catch(PatientNotFoundException e  ) {
			response = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
			
		return response;
		
	} */

	@Operation(summary = "View patient History")
	@GetMapping("/history/{patientId}")
	public ResponseEntity<?> viewPatientLiveData(@PathVariable (value = "patientId") UUID patientId) throws PatientNotFoundException{
		return new ResponseEntity<>(patientService.getPatientHistory(patientId), HttpStatus.OK);
	}

	@Operation(summary = "View patient LastData")
	@GetMapping("last/{patientId}")
	public ResponseEntity<?> getLastData(@PathVariable(value="patientId")UUID patientId) throws PatientNotFoundException {

		ResponseEntity<?> response;

		response = new ResponseEntity<>(patientService.getLastData(patientId),HttpStatus.OK);
		return response;
	}
	
	
	

 
}

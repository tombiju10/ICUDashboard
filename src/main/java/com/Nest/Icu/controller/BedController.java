package com.Nest.Icu.controller;

import com.Nest.Icu.exceptions.BedAlreadyExistsException;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.model.Bed;
import com.Nest.Icu.service.BedService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/icu/bed")
@CrossOrigin(origins = "*")
public class BedController {
	
	@Autowired
	private BedService bedService;
	
	
	@Operation(summary="Add Bed")
	@PostMapping("/add")
	public ResponseEntity<?> postBed(@RequestBody Bed bed) {
		
		ResponseEntity<?> response;
		
		try {
			response = new ResponseEntity<>(bedService.addBed(bed),
					HttpStatus.OK);
		} catch (BedAlreadyExistsException u) {

			response = new ResponseEntity<String>(u.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return response;		
	}

	@Operation(summary="Get Bed List")
	@GetMapping("/list")
	public List<Bed> getBedList(){
		return bedService.getBedsList();
	}
	
	
	@Operation(summary="Update Bed")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBed(@RequestBody Bed bed,@PathVariable("id") UUID uuid) throws BedNotFoundException  {
		
		ResponseEntity<?> response;
		
		try {
			response = new ResponseEntity<>(bedService.updateBed(bed, uuid), HttpStatus.OK);

		} catch (BedNotFoundException e) {

			response = new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}

		return response;
		
		
	}
	
	
	@Operation(summary="Delete Bed")
	@DeleteMapping("/delete/{id}")
	public String deleteBed(@PathVariable ("id") UUID uuid) throws BedNotFoundException {
		bedService.deleteBed(uuid);
		return "deleted";
	}


		
	@Operation(summary="Get Bed")
	@GetMapping("/{id}")
	public ResponseEntity<?> getBedData(@PathVariable ("id")UUID uuid) throws BedNotFoundException{
		return new ResponseEntity<>(bedService.getBedData(uuid), HttpStatus.OK);
	}
	
	
	

}

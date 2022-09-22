package com.Nest.Icu.mapper;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface PatientMapper {

    Patient addThePatient(PatientDto patientDto,@MappingTarget Patient patient);
    
}

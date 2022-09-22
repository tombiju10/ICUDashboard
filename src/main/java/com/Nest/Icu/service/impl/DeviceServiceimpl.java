package com.Nest.Icu.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.model.Agecondition;
import com.Nest.Icu.model.Patient;
import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.repository.PatientRepository;
import com.Nest.Icu.service.DeviceService;

@Service
public class DeviceServiceimpl implements DeviceService {

    @Autowired
    private PatientRepository patientRepository;

    // @Override
    public String addHistory(PatientHistory patientHistory, UUID patientid) throws PatientNotFoundException {

        if (!patientRepository.existsById(patientid)) {
            throw new PatientNotFoundException("Patiet not found with that id");
        }

        Agecondition ac = new Agecondition();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            Random rand = new Random();
            while (true) {
                double currentHb = ac.getMinHeartbeat()
                        + rand.nextDouble() * (ac.getMaxHeartbeat() - ac.getMinHeartbeat() + 1);
                double currentSp = ac.getMinSystolePressure()
                        + rand.nextDouble() * (ac.getMaxSystolePressure() - ac.getMinSystolePressure() + 1);
                double currentDp = ac.getMinDiastolePressure()
                        + rand.nextDouble() * (ac.getMaxDiastolePressure() - ac.getMinDiastolePressure() + 1);
                System.out.println(currentHb + " " + currentSp + "/" + currentSp);
                int HB = (int) currentHb;
                int DP = (int) currentDp;
                int SP = (int) currentSp;
                LocalDateTime now = LocalDateTime.now();
                patientHistory.setHeartbeat(HB);
                patientHistory.setDiastolepressure(DP);
                patientHistory.setSystolePressure(SP);
                patientHistory.setDate(dtf.format(now));
                Patient patient = patientRepository.findById(patientid).get();
                patient.getPatientHistory().add(patientHistory);
                patientRepository.save(patient);
                System.out.println(patient);
                patient.getPatientHistory().get(patient.getPatientHistory().size() - 1);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            System.out.println("Finished.");

        }
        return null;
    }

}



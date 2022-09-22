package com.Nest.Icu.repository;

import com.Nest.Icu.model.PatientHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientHistoryRepository extends JpaRepository<PatientHistory,Integer> {

}

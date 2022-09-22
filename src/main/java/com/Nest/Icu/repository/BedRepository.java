package com.Nest.Icu.repository;

import com.Nest.Icu.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BedRepository extends JpaRepository<Bed, UUID> {

}

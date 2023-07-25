package com.hh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hh.entity.Patient;
import com.hh.entity.Treatment;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {
	List<Treatment> findByPatient(Patient patient);
}

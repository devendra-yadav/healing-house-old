package com.hh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hh.entity.Session;
import com.hh.entity.Treatment;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
	List<Session> findByTreatment(Treatment treatment);
}

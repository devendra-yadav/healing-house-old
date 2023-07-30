package com.hh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hh.entity.Payment;
import com.hh.entity.Treatment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	Long removeByTreatment(Treatment treatment);
}

package com.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.trip.entity.Expense;

import jakarta.transaction.Transactional;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByTripId(Long tripId);

	@Transactional
	@Modifying
	void deleteByTripId(Long tripId);
}

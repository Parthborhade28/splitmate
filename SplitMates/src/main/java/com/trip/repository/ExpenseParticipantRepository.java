package com.trip.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.entity.ExpenseParticipant;

import java.util.List;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {
    List<ExpenseParticipant> findByExpenseId(Long expenseId);
}


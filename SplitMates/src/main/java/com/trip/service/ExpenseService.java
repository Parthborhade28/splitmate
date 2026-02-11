package com.trip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.entity.Expense;
import com.trip.entity.ExpenseParticipant;
import com.trip.repository.ExpenseParticipantRepository;
import com.trip.repository.ExpenseRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
	@Autowired
	ExpenseRepository expenseRepo;
	@Autowired
	ExpenseParticipantRepository participantRepo;

	
	@Transactional
	public void deleteAllExpenses(Long tripId){
	    expenseRepo.deleteByTripId(tripId);
	}


	public void addExpense(Long tripId, Long paidBy, Double amount, String desc, List<Long> participants) {

		Expense expense = new Expense(null, tripId, paidBy, amount, desc, null);
		Expense saved = expenseRepo.save(expense);

		for (Long userId : participants) {
			participantRepo.save(new ExpenseParticipant(null, saved.getId(), userId));
		}
	}

	public List<Expense> getTripExpenses(Long tripId) {
		return expenseRepo.findByTripId(tripId);
	}
}

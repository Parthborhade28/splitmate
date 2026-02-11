package com.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.entity.Expense;
import com.trip.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {
	@Autowired
	ExpenseService expenseService;

	@PostMapping
	public void addExpense(@RequestParam Long tripId, @RequestParam Long paidBy, @RequestParam Double amount,
			@RequestParam String desc, @RequestBody List<Long> users) {
		expenseService.addExpense(tripId, paidBy, amount, desc, users);
	}

	@GetMapping("/{tripId}")
	public List<Expense> getExpenses(@PathVariable Long tripId) {
		return expenseService.getTripExpenses(tripId);
	}
	@DeleteMapping("/trip/{tripId}")
	public void deleteTripExpenses(@PathVariable Long tripId){
	    expenseService.deleteAllExpenses(tripId);
	}

}

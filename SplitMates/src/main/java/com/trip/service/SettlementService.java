package com.trip.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.dto.SettlementDTO;
import com.trip.entity.Expense;
import com.trip.entity.TripMember;
import com.trip.entity.User;
import com.trip.repository.ExpenseRepository;
import com.trip.repository.TripMemberRepository;
import com.trip.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {
	@Autowired
	ExpenseRepository expenseRepo;
	@Autowired
	TripMemberRepository memberRepo;
	@Autowired
	UserRepository userRepo;

	public List<SettlementDTO> settle(Long tripId) {

		List<Expense> expenses = expenseRepo.findByTripId(tripId);
		List<TripMember> members = memberRepo.findByTripId(tripId);

		Map<Long, Double> paidMap = new HashMap<>();
		Map<Long, Double> balance = new HashMap<>();

		// initialize members
		for (TripMember m : members) {
			paidMap.put(m.getUserId(), 0.0);
			balance.put(m.getUserId(), 0.0);
		}

		double total = 0;

		// sum expenses
		for (Expense e : expenses) {
			double alreadyPaid = paidMap.getOrDefault(e.getPaidBy(), 0.0);
			paidMap.put(e.getPaidBy(), alreadyPaid + e.getAmount());
			total += e.getAmount();
		}

		double share = total / members.size();

		// calculate balance
		for (Long userId : paidMap.keySet()) {
			balance.put(userId, paidMap.get(userId) - share);
		}

		List<SettlementDTO> result = new ArrayList<>();

		// settlement logic
		// ⭐ REAL settlement algorithm

		List<Map.Entry<Long, Double>> creditors = new ArrayList<>();
		List<Map.Entry<Long, Double>> debtors = new ArrayList<>();

		for (Map.Entry<Long, Double> entry : balance.entrySet()) {
		    if (entry.getValue() > 0)
		        creditors.add(entry);
		    else if (entry.getValue() < 0)
		        debtors.add(entry);
		}

		// sort biggest amounts first
		creditors.sort((a,b) -> Double.compare(b.getValue(), a.getValue()));
		debtors.sort((a,b) -> Double.compare(a.getValue(), b.getValue()));

		int i = 0, j = 0;

		while (i < debtors.size() && j < creditors.size()) {

		    Long debtorId = debtors.get(i).getKey();
		    Long creditorId = creditors.get(j).getKey();

		    double debt = -debtors.get(i).getValue();
		    double credit = creditors.get(j).getValue();

		    double amountToPay = Math.min(debt, credit);

		    User fromUser = userRepo.findById(debtorId).orElseThrow();
		    User toUser = userRepo.findById(creditorId).orElseThrow();

		    result.add(new SettlementDTO(
		            fromUser.getName(),
		            toUser.getName(),
		            Math.round(amountToPay * 100.0) / 100.0
		    ));

		    // update balances
		    debtors.get(i).setValue(debtors.get(i).getValue() + amountToPay);
		    creditors.get(j).setValue(creditors.get(j).getValue() - amountToPay);

		    if (Math.abs(debtors.get(i).getValue()) < 0.01) i++;
		    if (Math.abs(creditors.get(j).getValue()) < 0.01) j++;
		}


		return result;
	}
}

package com.trip.service;

import com.trip.entity.Trip;
import com.trip.entity.TripMember;
import com.trip.repository.TripMemberRepository;
import com.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
	@Autowired
	TripRepository tripRepo;
	@Autowired
	TripMemberRepository memberRepo;

	public Trip createTrip(String name, Long userId) {
		Trip trip = new Trip();
		trip.setName(name);
		trip.setCreatedBy(userId);
		Trip saved = tripRepo.save(trip);

		memberRepo.save(new TripMember(null, saved.getId(), userId));
		return saved;
	}

	public void addMember(Long tripId, Long userId) {
		memberRepo.save(new TripMember(null, tripId, userId));
	}

	public List<TripMember> getMembers(Long tripId) {
		return memberRepo.findByTripId(tripId);
	}
	public List<Trip> getMyTrips(Long userId){
	    return tripRepo.findByCreatedBy(userId);
	}

}

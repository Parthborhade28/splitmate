package com.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.entity.Trip;
import com.trip.entity.TripMember;
import com.trip.service.TripService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TripController {
	@Autowired
	TripService tripService;

	@PostMapping
	public Trip createTrip(@RequestParam String name, @RequestParam Long userId) {
		return tripService.createTrip(name, userId);
	}
	@GetMapping("/my")
	public List<Trip> myTrips(@RequestParam Long userId){
	    return tripService.getMyTrips(userId);
	}


	@PostMapping("/{tripId}/add-member")
	public void addMember(@PathVariable Long tripId, @RequestParam Long userId) {
		tripService.addMember(tripId, userId);
	}

	@GetMapping("/{tripId}/members")
	public List<TripMember> getMembers(@PathVariable Long tripId) {
		return tripService.getMembers(tripId);
	}
}

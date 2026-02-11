package com.trip.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.entity.TripMember;

import java.util.List;

public interface TripMemberRepository extends JpaRepository<TripMember, Long> {
    List<TripMember> findByTripId(Long tripId);
}


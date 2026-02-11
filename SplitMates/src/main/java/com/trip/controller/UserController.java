package com.trip.controller;

import com.trip.entity.User;
import com.trip.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
	@Autowired
	UserRepository repo;

	@GetMapping("/by-email")
	public User getByEmail(@RequestParam String email){
	    return repo.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable Long id){
	    return repo.findById(id).orElseThrow();
	}

}

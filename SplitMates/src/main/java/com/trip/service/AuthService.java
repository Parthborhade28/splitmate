package com.trip.service;

import com.trip.dto.*;
import com.trip.entity.User;
import com.trip.repository.UserRepository;
import com.trip.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtil jwtUtil;

	public String register(AuthRequest req) {
		System.out.println(req.getEmail());
		User user = new User();
		user.setName(req.getName());
		user.setEmail(req.getEmail());
		user.setPassword(encoder.encode(req.getPassword()));
		userRepo.save(user);
		return jwtUtil.generateToken(user.getEmail());
	}

	public String login(AuthRequest req) {
		User user = userRepo.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

		if (!encoder.matches(req.getPassword(), user.getPassword()))
			throw new RuntimeException("Invalid password");

		return jwtUtil.generateToken(user.getEmail());
	}
}

package com.trip.controller;

import com.trip.dto.*;
import com.trip.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping("/register")
	public AuthResponse register(@RequestBody AuthRequest req){
	    String token = authService.register(req);
	    System.out.println("TOKEN = " + token);   // debug
	    return new AuthResponse(token);
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest req){
	    String token = authService.login(req);
	    System.out.println("TOKEN = " + token);   // debug
	    return new AuthResponse(token);
	}

}

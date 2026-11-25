package com.bookstore.controller;

import com.bookstore.entity.AppUser;
import com.bookstore.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService service;
	private final AuthenticationManager manager;

	public AuthController(AuthService s, AuthenticationManager m) {
		service = s;
		manager = m;
	}

	public record RegisterRequest(@NotBlank String name, @Email @NotBlank String email,
			@Size(min = 8) String password) {
	}

	public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {
	}

	public record UserResponse(Long id, String name, String email) {
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse register(@Valid @RequestBody RegisterRequest r) {
		AppUser u = service.register(r.name(), r.email(), r.password());
		return new UserResponse(u.getId(), u.getName(), u.getEmail());
	}

	@PostMapping("/login")
	public UserResponse login(@Valid @RequestBody LoginRequest r) {
		Authentication a = manager.authenticate(new UsernamePasswordAuthenticationToken(r.email(), r.password()));
		return new UserResponse(null, a.getName(), a.getName());
	}
}

package com.bookstore.service;

import com.bookstore.entity.AppUser;
import com.bookstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	private final UserRepository repo;
	private final PasswordEncoder encoder;

	public AuthService(UserRepository r, PasswordEncoder e) {
		repo = r;
		encoder = e;
	}

	public AppUser register(String name, String email, String password) {
		if (repo.findByEmail(email).isPresent())
			throw new IllegalArgumentException("Email already registered");
		return repo.save(new AppUser(name, email, encoder.encode(password)));
	}
}

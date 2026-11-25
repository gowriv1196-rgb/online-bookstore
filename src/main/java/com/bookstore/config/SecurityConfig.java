package com.bookstore.config;

import com.bookstore.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(UserRepository repo) {
		return email -> repo.findByEmail(email)
				.map(u -> User.withUsername(u.getEmail()).password(u.getPassword()).roles("USER").build())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
		DaoAuthenticationProvider p = new DaoAuthenticationProvider();
		p.setUserDetailsService(uds);
		p.setPasswordEncoder(encoder);
		return p;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider provider) throws Exception {
		return http.csrf(csrf -> csrf.disable()).headers(h -> h.frameOptions(f -> f.sameOrigin()))
				.authenticationProvider(provider)
				.authorizeHttpRequests(a -> a.requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/books/**").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).build();
	}
}

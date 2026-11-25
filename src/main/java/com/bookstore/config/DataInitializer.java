package com.bookstore.config;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import java.math.BigDecimal;

@Configuration
public class DataInitializer {
	@Bean
	CommandLineRunner seedBooks(BookRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				repo.save(new Book("Clean Code", "Robert C. Martin", new BigDecimal("550.00"), 10));
				repo.save(new Book("Effective Java", "Joshua Bloch", new BigDecimal("700.00"), 8));
				repo.save(new Book("The Pragmatic Programmer", "Andrew Hunt", new BigDecimal("620.00"), 5));
			}
		};
	}
}

package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
	private final BookRepository repo;

	public BookService(BookRepository repo) {
		this.repo = repo;
	}

	public List<Book> findAll() {
		return repo.findAll();
	}

	public Book findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
	}
}

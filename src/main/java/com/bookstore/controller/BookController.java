package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
	private final BookService service;

	public BookController(BookService s) {
		service = s;
	}

	@GetMapping
	public List<Book> all() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Book one(@PathVariable Long id) {
		return service.findById(id);
	}
}

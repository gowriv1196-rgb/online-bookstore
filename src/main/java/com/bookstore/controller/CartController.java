package com.bookstore.controller;

import com.bookstore.entity.CartItem;
import com.bookstore.service.CartService;
import com.bookstore.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	private final CartService service;
	private final UserRepository users;

	public CartController(CartService s, UserRepository users) {
		service = s;
		this.users = users;
	}

	public record AddRequest(@Min(1) int quantity) {
	}

	public record QuantityRequest(@Min(1) int quantity) {
	}

	private Long uid(Authentication a) {
		return users.findByEmail(a.getName()).orElseThrow().getId();
	}

	@GetMapping
	public List<CartItem> get(Authentication a) {
		return service.getCart(uid(a));
	}

	@PostMapping("/items")
	@ResponseStatus(HttpStatus.CREATED)
	public CartItem add(Authentication a, @RequestParam Long bookId, @Valid @RequestBody AddRequest r) {
		return service.add(uid(a), bookId, r.quantity());
	}

	@PutMapping("/items/{bookId}")
	public CartItem update(Authentication a, @PathVariable Long bookId, @Valid @RequestBody QuantityRequest r) {
		return service.update(uid(a), bookId, r.quantity());
	}

	@DeleteMapping("/items/{bookId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(Authentication a, @PathVariable Long bookId) {
		service.remove(uid(a), bookId);
	}
}

package com.bookstore.controller;

import com.bookstore.entity.*;
import com.bookstore.service.OrderService;
import com.bookstore.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService service;
	private final UserRepository users;

	public OrderController(OrderService s, UserRepository users) {
		service = s;
		this.users = users;
	}

	private Long uid(Authentication a) {
		return users.findByEmail(a.getName()).orElseThrow().getId();
	}

	@PostMapping("/checkout")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerOrder checkout(Authentication a) {
		return service.checkout(uid(a));
	}

	@GetMapping
	public List<CustomerOrder> all(Authentication a) {
		return service.getOrders(uid(a));
	}

	@GetMapping("/{id}/items")
	public List<OrderItem> items(@PathVariable Long id) {
		return service.getItems(id);
	}
}

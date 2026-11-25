package com.bookstore.service;

import com.bookstore.entity.*;
import com.bookstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CartService {
	private final CartRepository carts;
	private final BookRepository books;

	public CartService(CartRepository c, BookRepository b) {
		carts = c;
		books = b;
	}

	public List<CartItem> getCart(Long uid) {
		return carts.findByUserId(uid);
	}

	@Transactional
	public CartItem add(Long uid, Long bookId, int qty) {
		if (qty < 1)
			throw new IllegalArgumentException("Quantity must be at least 1");
		Book b = books.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
		CartItem item = carts.findByUserIdAndBookId(uid, bookId).orElse(new CartItem(uid, b, 0));
		if (item.getQuantity() + qty > b.getStock())
			throw new IllegalArgumentException("Insufficient stock");
		item.setQuantity(item.getQuantity() + qty);
		return carts.save(item);
	}

	@Transactional
	public CartItem update(Long uid, Long bookId, int qty) {
		if (qty < 1)
			throw new IllegalArgumentException("Quantity must be at least 1");
		CartItem item = carts.findByUserIdAndBookId(uid, bookId)
				.orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
		if (qty > item.getBook().getStock())
			throw new IllegalArgumentException("Insufficient stock");
		item.setQuantity(qty);
		return carts.save(item);
	}

	public void remove(Long uid, Long bookId) {
		CartItem i = carts.findByUserIdAndBookId(uid, bookId)
				.orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
		carts.delete(i);
	}
}

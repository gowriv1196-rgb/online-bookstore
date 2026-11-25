package com.bookstore.service;

import com.bookstore.entity.*;
import com.bookstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {
	private final CartRepository carts;
	private final OrderRepository orders;
	private final OrderItemRepository items;
	private final BookRepository books;

	public OrderService(CartRepository c, OrderRepository o, OrderItemRepository i, BookRepository b) {
		carts = c;
		orders = o;
		items = i;
		books = b;
	}

	@Transactional
	public CustomerOrder checkout(Long uid) {
		List<CartItem> cart = carts.findByUserId(uid);
		if (cart.isEmpty())
			throw new IllegalArgumentException("Cart is empty");
		BigDecimal total = BigDecimal.ZERO;
		for (CartItem ci : cart) {
			if (ci.getQuantity() > ci.getBook().getStock())
				throw new IllegalArgumentException("Insufficient stock for " + ci.getBook().getTitle());
			total = total.add(ci.getBook().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
		}
		CustomerOrder order = orders.save(new CustomerOrder(uid, total));
		for (CartItem ci : cart) {
			Book b = ci.getBook();
			items.save(new OrderItem(order.getId(), b.getId(), b.getTitle(), ci.getQuantity(), b.getPrice()));
			b.setStock(b.getStock() - ci.getQuantity());
			books.save(b);
		}
		carts.deleteByUserId(uid);
		return order;
	}

	public List<CustomerOrder> getOrders(Long uid) {
		return orders.findByUserId(uid);
	}

	public List<OrderItem> getItems(Long orderId) {
		return items.findByOrderId(orderId);
	}
}

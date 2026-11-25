package com.bookstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long orderId;
	private Long bookId;
	private String title;
	private int quantity;
	private BigDecimal unitPrice;

	public OrderItem() {
	}

	public OrderItem(Long orderId, Long bookId, String title, int quantity, BigDecimal price) {
		this.orderId = orderId;
		this.bookId = bookId;
		this.title = title;
		this.quantity = quantity;
		this.unitPrice = price;
	}

	public Long getId() {
		return id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
}

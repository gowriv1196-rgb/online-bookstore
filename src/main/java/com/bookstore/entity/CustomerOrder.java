package com.bookstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private BigDecimal totalAmount;
	private LocalDateTime orderDate;
	private String status;

	public CustomerOrder() {
	}

	public CustomerOrder(Long uid, BigDecimal total) {
		userId = uid;
		totalAmount = total;
		orderDate = LocalDateTime.now();
		status = "PLACED";
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public String getStatus() {
		return status;
	}
}

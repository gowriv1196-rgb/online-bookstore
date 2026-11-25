package com.bookstore.entity;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "book_id" }))
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_id", nullable = false)
	private Long userId;
	@ManyToOne(optional = false)
	private Book book;
	private int quantity;

	public CartItem() {
	}

	public CartItem(Long userId, Book book, int quantity) {
		this.userId = userId;
		this.book = book;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Book getBook() {
		return book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int q) {
		quantity = q;
	}
}

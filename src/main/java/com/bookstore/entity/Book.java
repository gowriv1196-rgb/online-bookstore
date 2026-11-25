package com.bookstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String title;
	@NotBlank
	private String author;
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price;
	@Min(0)
	private int stock;

	public Book() {
	}

	public Book(String title, String author, BigDecimal price, int stock) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String v) {
		title = v;
	}

	public void setAuthor(String v) {
		author = v;
	}

	public void setPrice(BigDecimal v) {
		price = v;
	}

	public void setStock(int v) {
		stock = v;
	}
}

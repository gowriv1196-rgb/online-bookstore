package com.bookstore.service;

import com.bookstore.entity.*;
import com.bookstore.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
	@Mock
	CartRepository carts;
	@Mock
	BookRepository books;
	@InjectMocks
	CartService service;

	@Test
	void addRejectsZeroQuantity() {
		assertThrows(IllegalArgumentException.class, () -> service.add(1L, 1L, 0));
	}

	@Test
	void addRejectsUnknownBook() {
		when(books.findById(9L)).thenReturn(Optional.empty());
		assertThrows(IllegalArgumentException.class, () -> service.add(1L, 9L, 1));
	}

	@Test
	void addCreatesCartItem() {
		Book b = new Book("Java", "Joshua", new BigDecimal("100"), 5);
		when(books.findById(1L)).thenReturn(Optional.of(b));
		when(carts.findByUserIdAndBookId(1L, 1L)).thenReturn(Optional.empty());
		when(carts.save(any())).thenAnswer(x -> x.getArgument(0));
		CartItem result = service.add(1L, 1L, 2);
		assertEquals(2, result.getQuantity());
	}
}

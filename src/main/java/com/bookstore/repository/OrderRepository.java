package com.bookstore.repository;

import com.bookstore.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findByUserId(Long userId);
}

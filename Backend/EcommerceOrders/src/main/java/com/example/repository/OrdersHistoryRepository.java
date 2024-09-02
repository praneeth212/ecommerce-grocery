package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.OrdersHistory;

public interface OrdersHistoryRepository extends JpaRepository<OrdersHistory, String> {
	
	List<OrdersHistory> findByUserId(Long userId);

	List<OrdersHistory> findByStatus(String status);
}

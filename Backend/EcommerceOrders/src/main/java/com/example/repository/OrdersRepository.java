package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, String> {
	
	List<Orders> findByUserId(Long userId);

	List<Orders> findByStatus(String status);
	
}
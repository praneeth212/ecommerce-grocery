package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, String> {

	
}
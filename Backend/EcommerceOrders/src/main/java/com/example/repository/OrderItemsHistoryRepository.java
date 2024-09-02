package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.OrderItemsHistory;

public interface OrderItemsHistoryRepository extends JpaRepository<OrderItemsHistory, String> {

	
}

package com.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

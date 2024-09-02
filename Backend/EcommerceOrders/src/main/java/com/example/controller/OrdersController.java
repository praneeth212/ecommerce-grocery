package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Orders;
import com.example.model.OrdersHistory;
import com.example.service.OrdersService;

@RestController
@CrossOrigin(origins="*")
public class OrdersController {
	
	private final OrdersService ordersService;
	public OrdersController(OrdersService ordersService) {
		this.ordersService = ordersService;
	}
	
	@PostMapping("/orders")
	public String createOrderHandler(@RequestBody Orders orders) {
	  return ordersService.saveOrders(orders,true);
	}
	
	@GetMapping("/orders/users/{userId}")
	public List<Orders> userOrderHistoryHandler(@PathVariable Long userId){
		return ordersService.getOrdersByUserId(userId);
	}
	
	@GetMapping("/ordersHistory/users/{userId}")
	public List<OrdersHistory> userOrderHistory(@PathVariable Long userId){
		return ordersService.getOrdersHistoryByUserId(userId);
	}
	
	@GetMapping("/orders/{orderId}")
	public Orders findOrderHistorHandler(@PathVariable String orderId){
		return ordersService.getOrderById(orderId);
	}
	
	@GetMapping("/orders/status/{status}")
	public List<Orders> ordersByStatusHandler(@PathVariable String status){
		return ordersService.getOrdersByStatus(status);
	}
	
	@PutMapping("/orders/changeStatus/{orderId}/{status}")
	public void updateOrderStatus(@PathVariable String orderId,@PathVariable String status){
	       ordersService.changeOrderStatus(orderId,status);
	}
	
}

package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.OrderItems;
import com.example.model.OrderItemsHistory;
import com.example.model.Orders;
import com.example.model.OrdersHistory;
import com.example.model.Product;
import com.example.repository.OrdersHistoryRepository;
import com.example.repository.OrdersRepository;


@Service
public class OrdersService {
	
	private final OrdersRepository ordersRepository;
	
	private final OrdersHistoryRepository ordersHistoryRepository;
	
	private final RestTemplate restTemplate;
	
	public OrdersService(OrdersRepository ordersRepository, OrdersHistoryRepository ordersHistoryRepository,RestTemplate restTemplate) {
		this.ordersRepository = ordersRepository;
		this.ordersHistoryRepository = ordersHistoryRepository;
		this.restTemplate = restTemplate;
	}
	
	private static final String PRODUCT_URL = "http://localhost:8082/api/product/";
	
	private static final String CART_URL = "http://localhost:8084/cart/deleteall/";
	
	public List<Orders> getOrdersByUserId(Long userId) {
        List<Orders> orders = ordersRepository.findByUserId(userId);
        for (Orders order : orders) {
            for (OrderItems item : order.getItems()) {
                Product product = restTemplate.getForObject(PRODUCT_URL+item.getProduct().getId() , Product.class);
                item.setProductName(product.getProdName());
            }
        }
        return orders;
    }
	
	public List<OrdersHistory> getOrdersHistoryByUserId(Long userId) {
		List<OrdersHistory> ordersHistory = ordersHistoryRepository.findByUserId(userId);
		for(OrdersHistory order: ordersHistory) {
		for(OrderItemsHistory item: order.getItems()) {
		Product product = restTemplate.getForObject(PRODUCT_URL+item.getProduct().getId(),Product.class);
		item.setProductName(product.getProdName());
		}
		}
		return ordersHistory;
	}
	
	public String saveOrders(Orders order,Boolean newOrder) {
		String randomId = UUID.randomUUID().toString();
		if(newOrder) {
		order.setOrderId(randomId);
		for(OrderItems item: order.getItems()) {
			String randomId1 = UUID.randomUUID().toString();
			item.setOrderItemId(randomId1);
			item.setOrder(order);
		}
		restTemplate.delete(CART_URL+order.getUserId());
		}
		ordersRepository.save(order);
		return order.getOrderId();
	}
	
	public void saveOrdersHistory(Orders order) {
		OrdersHistory orderHist = new OrdersHistory();
		orderHist.setOrderHistId(order.getOrderId());
		orderHist.setDiscount(order.getDiscount());
		orderHist.setOrderDate(order.getOrderDate());
		orderHist.setStatus(order.getStatus());
		orderHist.setTotalItem(order.getTotalItem());
		orderHist.setTotalPrice(order.getTotalPrice());
		List<OrderItemsHistory> items = saveOrderItemsHistory(order,orderHist);
		orderHist.setItems(items);
		ordersHistoryRepository.save(orderHist);
	}
	
	public List<OrderItemsHistory> saveOrderItemsHistory(Orders order,OrdersHistory orderHist) {
		List<OrderItemsHistory> itemsHist = new ArrayList<OrderItemsHistory>();
		for(OrderItems item: order.getItems()) {
			OrderItemsHistory itemHist = new OrderItemsHistory();
			itemHist.setOrderItemHistId(item.getOrderItemId());
			itemHist.setProduct(item.getProduct());
			itemHist.setDeliveryDate(item.getDeliveryDate());
			itemHist.setDiscountedPrice(item.getDiscountedPrice());
			itemHist.setPrice(item.getPrice());
			itemHist.setQuantity(item.getQuantity());
			itemHist.setUnit(item.getUnit());
			itemHist.setOrderHistory(orderHist);
			itemsHist.add(itemHist);
		}
		return itemsHist;
	}
	
	public List<Orders> getOrdersByStatus(String status) {
		List<Orders> orders = ordersRepository.findByStatus(status);
		for(Orders order: orders) {
			for(OrderItems item: order.getItems()) {
				Product product = restTemplate.getForObject(PRODUCT_URL+item.getProduct().getId(),Product.class);
				item.setProductName(product.getProdName());
			}
		}
		return orders;
	}
	
	public Orders getOrderById(String orderId) {
		Orders order = ordersRepository.findById(orderId).get();
		for(OrderItems item: order.getItems()) {
			Product product = restTemplate.getForObject(PRODUCT_URL+item.getProduct().getId(),Product.class);
			item.setProductName(product.getProdName());
		}
		return order;
	}
	
	public void changeOrderStatus(String orderId, String status) {
		Orders order = ordersRepository.findById(orderId).get();
		order.setStatus(status);
		if(status.equals("delivered")) {
			saveOrdersHistory(order);
			ordersRepository.delete(order);
		}else {
			saveOrders(order,false);
		}
	}
	
}

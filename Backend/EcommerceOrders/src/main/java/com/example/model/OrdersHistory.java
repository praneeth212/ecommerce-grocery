package com.example.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class OrdersHistory {
	
	@Id
	private String orderHistId;
	private Long userId;
	private Long discount;
	private Long totalPrice;
	private Date orderDate;
	private String totalItem;
	private String status;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="orderHistory",cascade= CascadeType.ALL)
    private List<OrderItemsHistory> items;
		
	public OrdersHistory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "OrdersHistory [orderHistId=" + orderHistId + ", userId=" + userId + ", discount=" + discount
				+ ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + ", totalItem=" + totalItem + ", status="
				+ status + ", items=" + items + "]";
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderHistId() {
		return orderHistId;
	}

	public void setOrderHistId(String orderHistId) {
		this.orderHistId = orderHistId;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	
	

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderItemsHistory> getItems() {
		return items;
	}

	public void setItems(List<OrderItemsHistory> items) {
		this.items = items;
	}
	
}

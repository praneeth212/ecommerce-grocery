package com.grocery.modal;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Orders {
	
	@Id
	private String orderId;
	private Long userId;
	private Long discount;
	private Long totalPrice;
	private Date orderDate;
	private String totalItem;
	private String status;
	@Transient
	private Cart cart;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="order",cascade= CascadeType.ALL)
    private List<OrderItems> items;
		
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", userId=" + userId + ", discount=" + discount 
				+ ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + ", totalItem=" + totalItem
				+ ", status=" + status + ", cart=" + cart + ", items=" + items + "]";
	}
	
	public String getOrderId() {
		return orderId;
	}

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<OrderItems> getItems() {
		return items;
	}

	public void setItems(List<OrderItems> items) {
		this.items = items;
	}
	
}

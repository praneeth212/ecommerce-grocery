package com.example.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;


@Entity
public class OrderItems {
	
	@Id
	private String orderItemId;
	@Transient
	private String productName;
	private String unit;
	private String quantity;
	private Long price;
	private Long discountedPrice;
	private Date deliveryDate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders order;
	
	 @ManyToOne
	 @JsonBackReference
	    @JoinColumn(name = "product_id", nullable = false)
	    private Product product;
	
	public OrderItems() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OrderItems [orderItemId=" + orderItemId + ", productName=" + productName
				+ ", unit=" + unit + ", quantity=" + quantity + ", price=" + price + ", discountedPrice="
				+ discountedPrice + ", deliveryDate=" + deliveryDate + ", order=" + order + ", product=" + product
				+ "]";
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Long discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
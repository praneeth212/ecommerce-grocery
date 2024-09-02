package com.example.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	private Long userId;
    private Long productItemId;
    private String prodName;
    private int unitId;
    private String unitname;
    private double price;
    private double discountPrice;
    private int quantity;
    
    public Long getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductItemId() {
		return productItemId;
	}
	public void setProductItemId(Long productItemId) {
		this.productItemId = productItemId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getUnitId() {
		return unitId;
	}
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Cart [cartItemId=" + cartItemId + ", userId=" + userId + ", productItemId=" + productItemId
				+ ", prodName=" + prodName + ", unitId=" + unitId + ", unitname=" + unitname + ", price=" + price
				+ ", discountPrice=" + discountPrice + ", quantity=" + quantity + "]";
	}
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
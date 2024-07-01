package com.grocery.service;

import com.grocery.exception.ProductException;
import com.grocery.modal.Cart;
import com.grocery.modal.CartItem;
import com.grocery.modal.User;
import com.grocery.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
	
}

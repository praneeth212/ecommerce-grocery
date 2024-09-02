package com.example.service;

import com.example.dto.ProductDetailDTO;
import com.example.dto.ToCart;
import com.example.exceptions.ProductNotFound;
import com.example.model.Cart;
import com.example.model.ProductItem;
import com.example.model.User;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;
    private RestTemplate restTemplate;

    @Autowired
    public CartService(CartRepository cartRepository, RestTemplate restTemplate) {
        this.cartRepository = cartRepository;
        this.restTemplate = restTemplate;
    }
    
    private final String USER_SERVICE_URL = "http://localhost:8085/users/";
    private final String PRODUCT_ITEM_SERVICE_URL = "http://localhost:8082/api/productitems/";
    
    public synchronized void addToCart(ToCart toCart) {
        Cart existingCartItem = cartRepository.findByProductItemId(toCart.getProductItemId());
        ProductDetailDTO productDetail = restTemplate.getForObject(PRODUCT_ITEM_SERVICE_URL + toCart.getProductItemId(), ProductDetailDTO.class);
        if (productDetail == null) {
            throw new ProductNotFound("Product not found");
        }

        User user = restTemplate.getForObject(USER_SERVICE_URL + toCart.getUserId(), User.class);
        if (user == null) {
            throw new ProductNotFound("User not found");
        }

        int requestedQuantity = toCart.getQuantity();
        int availableQuantity = productDetail.getQuantityInStock();

        if (requestedQuantity > availableQuantity) {
            throw new ProductNotFound("Requested quantity exceeds available stock");
        }

        if (existingCartItem != null) {
            // Update existing cart item
            int currentQuantity = existingCartItem.getQuantity();
            int newQuantity = currentQuantity + requestedQuantity;
            int updatedQuantityInStock = availableQuantity - requestedQuantity;

            existingCartItem.setQuantity(newQuantity);
            existingCartItem.setQuantityInStock(updatedQuantityInStock);

            cartRepository.save(existingCartItem);
        } else {
            // Add new cart item
            Cart cartItem = new Cart();
            cartItem.setUser(user);
            ProductItem productItem = new ProductItem();
            productItem.setId(productDetail.getProductItemId());
            cartItem.setProductItem(productItem);
            cartItem.setProductImageUrl(productDetail.getProductImg());
            cartItem.setProdName(toCart.getProdName());
            cartItem.setUnitId(toCart.getUnitId());
            cartItem.setUnitname(toCart.getUnitname());
            cartItem.setPrice(toCart.getPrice());
            cartItem.setDiscountPrice(toCart.getDiscountPrice());
            cartItem.setQuantity(toCart.getQuantity());
            cartItem.setQuantityInStock(availableQuantity - requestedQuantity);

            cartRepository.save(cartItem);
        }

        // Update the product item's stock quantity
        restTemplate.put(PRODUCT_ITEM_SERVICE_URL + "quantity/" + productDetail.getProductItemId() + "/" + (availableQuantity - requestedQuantity), null);
    }

    public void deleteFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public synchronized  void updateCartItemQuantity(Long productItemId, int quantity) {
        Cart cartItem = cartRepository.findByProductItemId(productItemId);
        if (cartItem != null) {
            int currentQuantity = cartItem.getQuantity();
            int difference = quantity - currentQuantity;

            // Fetch the ProductItem details using RestTemplate
            ProductItem productItem = restTemplate.getForObject(PRODUCT_ITEM_SERVICE_URL + productItemId, ProductItem.class);

            if (productItem == null) {
                throw new ProductNotFound("Product not found");
            }

            if (difference > productItem.getQuantityInStock()) {
                throw new ProductNotFound("Requested quantity exceeds available stock");
            }

            // Update the quantity in stock using RestTemplate PUT method
            String updateUrl = PRODUCT_ITEM_SERVICE_URL + "quantity/" + productItemId + "/" + (productItem.getQuantityInStock() - difference);
            restTemplate.put(updateUrl, null);

            cartItem.setQuantity(quantity);
            cartItem.setQuantityInStock(productItem.getQuantityInStock() - difference);

            cartRepository.save(cartItem);
        } else {
            throw new ProductNotFound("Cart item with id " + productItemId + " not found");
        }
    }

    public List<Cart> listCartItems() {
        return cartRepository.findAll();
    }

    public List<Cart> listCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public synchronized void deleteAll(Long userId) {
    	List<Cart> cartItems = cartRepository.findByUserId(userId);
		for(Cart cartItem: cartItems) {
			cartRepository.delete(cartItem);
		}
    }

    public synchronized void deleteCartItemByProductItemId(Long productItemId) {
        Cart cartItem = cartRepository.findByProductItemId(productItemId);
        if (cartItem != null) {
            int cartItemQuantity = cartItem.getQuantity();

            // Fetch the ProductDetailDTO using RestTemplate
            ProductDetailDTO productDetail = restTemplate.getForObject(PRODUCT_ITEM_SERVICE_URL + productItemId, ProductDetailDTO.class);

            if (productDetail == null) {
                throw new ProductNotFound("Product not found");
            }

            // Add back the quantity of the cart item to the ProductItem stock
            int updatedStock = productDetail.getQuantityInStock() + cartItemQuantity;

            // Update the ProductDetailDTO with the new stock quantity
            productDetail.setQuantityInStock(updatedStock);

            // Update the quantity in stock using RestTemplate PUT method
            restTemplate.put(PRODUCT_ITEM_SERVICE_URL + "quantity/" + productItemId + "/" + updatedStock, null);

            // Delete the cart item
            cartRepository.delete(cartItem);
        } else {
            throw new ProductNotFound("Cart item with id " + productItemId + " not found");
        }
    }
}

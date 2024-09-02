package com.example.controller;

import com.example.dto.ToCart;
import com.example.exceptions.ProductAlreadyExists;
import com.example.exceptions.ProductNotFound;
import com.example.model.Cart;
import com.example.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody ToCart toCart) {
        try {
            cartService.addToCart(toCart);
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (ProductAlreadyExists e) {
            return ResponseEntity.badRequest().body("Failed to add product to cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<String> deleteFromCart(@PathVariable Long cartItemId) {
        cartService.deleteFromCart(cartItemId);
        return ResponseEntity.ok("Product deleted from cart successfully");
    }

    @DeleteMapping("/deleteproduct/{productItemId}")
    public ResponseEntity<String> deleteCartItemByProductItemId(@PathVariable Long productItemId) {
        try {
            cartService.deleteCartItemByProductItemId(productItemId);
            return ResponseEntity.ok("Product deleted from cart successfully");
        } catch (ProductNotFound e) {
            return ResponseEntity.badRequest().body("Failed to delete product from cart: " + e.getMessage());
        }
    }

    @PutMapping("/update/{productItemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable Long productItemId,
                                                         @RequestBody Map<String, Integer> quantityData) {
        try {
            int quantity = quantityData.get("quantity");
            cartService.updateCartItemQuantity(productItemId, quantity);
            return ResponseEntity.ok("Cart item quantity updated successfully");
        } catch (ProductNotFound e) {
            return ResponseEntity.badRequest().body("Failed to update cart item quantity: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Cart>> listCartItems() {
        List<Cart> cartList = cartService.listCartItems();
        return ResponseEntity.ok(cartList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> listCartItemsByUserId(@PathVariable Long userId) {
        List<Cart> cartList = cartService.listCartItemsByUserId(userId);
        return ResponseEntity.ok(cartList);
    }

    @DeleteMapping("/deleteall/{userId}")
    public ResponseEntity<String> deleteAll(@PathVariable Long userId) {
        cartService.deleteAll(userId);
        return ResponseEntity.ok("All cart items deleted successfully");
    }
}

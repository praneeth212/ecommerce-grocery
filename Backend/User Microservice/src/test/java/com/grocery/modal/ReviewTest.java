package com.grocery.modal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class ReviewTest {

    private Review review;
    private User user;
    private Product product;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        // Create user and product instances
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        // Create LocalDateTime instance for testing
        dateTime = LocalDateTime.now();

        // Set up Review
        review = new Review();
        review.setId(1L);
        review.setReview("Excellent product!");
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(dateTime);
    }

    @Test
    void getId() {
        assertEquals(1L, review.getId());
    }

    @Test
    void getReview() {
        assertEquals("Excellent product!", review.getReview());
    }

    @Test
    void getUser() {
        assertEquals(user, review.getUser());
    }

    @Test
    void getProduct() {
        assertEquals(product, review.getProduct());
    }

    @Test
    void getCreatedAt() {
        assertEquals(dateTime, review.getCreatedAt());
    }

    @Test
    void setId() {
        review.setId(2L);
        assertEquals(2L, review.getId());
    }

    @Test
    void setReview() {
        review.setReview("Needs improvement");
        assertEquals("Needs improvement", review.getReview());
    }

    @Test
    void setUser() {
        User newUser = new User();
        newUser.setId(2L);
        review.setUser(newUser);
        assertEquals(newUser, review.getUser());
    }

    @Test
    void setProduct() {
        Product newProduct = new Product();
        newProduct.setId(2L);
        review.setProduct(newProduct);
        assertEquals(newProduct, review.getProduct());
    }

    @Test
    void setCreatedAt() {
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        review.setCreatedAt(newDateTime);
        assertEquals(newDateTime, review.getCreatedAt());
    }
}

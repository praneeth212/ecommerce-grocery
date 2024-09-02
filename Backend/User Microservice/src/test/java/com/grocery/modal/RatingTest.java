package com.grocery.modal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class RatingTest {

    private Rating rating;
    private User user;
    private Product product;
    private LocalDateTime time;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        time = LocalDateTime.now();

        rating = new Rating();
        rating.setId(1L);
        rating.setUser(user);
        rating.setProduct(product);
        rating.setRating(4.5);
        rating.setCreatedAt(time);
    }

    @Test
    void testGetId() {
        assertEquals(1L, rating.getId());
    }

    @Test
    void testGetUser() {
        assertNotNull(rating.getUser());
        assertEquals(user, rating.getUser());
    }

    @Test
    void testGetProduct() {
        assertNotNull(rating.getProduct());
        assertEquals(product, rating.getProduct());
    }

    @Test
    void testGetRating() {
        assertEquals(4.5, rating.getRating());
    }

    @Test
    void testGetCreatedAt() {
        assertEquals(time, rating.getCreatedAt());
    }

    @Test
    void testSetId() {
        rating.setId(2L);
        assertEquals(2L, rating.getId());
    }

    @Test
    void testSetUser() {
        User newUser = new User();
        newUser.setId(2L);
        rating.setUser(newUser);
        assertEquals(newUser, rating.getUser());
    }

    @Test
    void testSetProduct() {
        Product newProduct = new Product();
        newProduct.setId(2L);
        rating.setProduct(newProduct);
        assertEquals(newProduct, rating.getProduct());
    }

    @Test
    void testSetRating() {
        double newRating = 3.5;
        rating.setRating(newRating);
        assertEquals(3.5, rating.getRating());
    }

    @Test
    void testSetCreatedAt() {
        LocalDateTime newTime = LocalDateTime.now().plusDays(1);
        rating.setCreatedAt(newTime);
        assertEquals(newTime, rating.getCreatedAt());
    }
}

package com.grocery.request;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReviewRequestTest {

    private ReviewRequest reviewRequest;

    @BeforeEach
    public void setUp() {
        reviewRequest = new ReviewRequest();
    }

    @Test
    public void testGetAndSetProductId() {
        Long productId = 123L;
        assertNull(reviewRequest.getProductId());
        reviewRequest.setProductId(productId);
        assertEquals(productId, reviewRequest.getProductId());
    }

    @Test
    public void testGetAndSetReview() {
        String review = "This is a great product!";
        assertNull(reviewRequest.getReview());
        reviewRequest.setReview(review);
        assertEquals(review, reviewRequest.getReview());
    }
}

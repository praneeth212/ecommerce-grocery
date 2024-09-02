package com.grocery.service;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grocery.exception.ProductNotFoundException;
import com.grocery.exception.UserException;
import com.grocery.modal.Product;
import com.grocery.modal.Review;
import com.grocery.modal.User;
import com.grocery.repository.ReviewRepository;
import com.grocery.request.ReviewRequest;

@Service
public class ReviewService {
	
	private ReviewRepository reviewRepository;
	private RestTemplate restTemplate;
	
	public ReviewService(ReviewRepository reviewRepository, RestTemplate restTemplate) {
		this.reviewRepository=reviewRepository;
		this.restTemplate = restTemplate;
	}
	
	 private static final String PRODUCT_SERVICE_URL = "http://localhost:8082/api/products/product/";
	 private static final String USER_SERVICE_URL = "http://localhost:8085/users/email/";


	    public Review createReview(ReviewRequest req) throws ProductNotFoundException, UserException {
	        // Fetch the Product using RestTemplate
	        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + req.getProductId(), Product.class);
	        if (product == null) {
	            throw new ProductNotFoundException("Product not found with id: " + req.getProductId());
	        }

	        // Fetch the User using RestTemplate
	        User user = restTemplate.getForObject(USER_SERVICE_URL + req.getEmail(), User.class);
	        if (user == null) {
	            throw new UserException("User not found with email: " + req.getEmail());
	        }

	        Review review = new Review();
	        review.setUser(user);
	        review.setProduct(product);
	        review.setReview(req.getReview());
	        review.setCreatedAt(LocalDateTime.now());

	        return reviewRepository.save(review);
	    }

	public List<Review> getAllReview(Long productId) {
		return reviewRepository.getAllProductsReview(productId);
	}

}

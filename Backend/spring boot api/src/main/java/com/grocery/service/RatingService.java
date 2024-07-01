package com.grocery.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grocery.exception.ProductException;
import com.grocery.modal.Product;
import com.grocery.modal.Rating;
import com.grocery.modal.User;
import com.grocery.repository.RatingRepository;
import com.grocery.request.RatingRequest;

@Service
public class RatingService{
	
	private RatingRepository ratingRepository;
	private ProductService productService;
	
	public RatingService(RatingRepository ratingRepository,ProductService productService) {
		this.ratingRepository=ratingRepository;
		this.productService=productService;
	}

	public Rating createRating(RatingRequest req,User user) throws ProductException {
		
		Product product=productService.findProductById(req.getProductId());
		
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	public List<Rating> getProductsRating(Long productId) {
		// TODO Auto-generated method stub
		return ratingRepository.getAllProductsRating(productId);
	}
	
	

}

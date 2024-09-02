package com.grocery.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grocery.modal.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query(value = "Select * from review r where r.product_id=:productId", nativeQuery=true)
	public List<Review> getAllProductsReview(@Param("productId") Long productId);
}

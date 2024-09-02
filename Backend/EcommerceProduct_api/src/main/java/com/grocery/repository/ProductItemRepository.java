package com.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grocery.model.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long>{
	
	
	
}

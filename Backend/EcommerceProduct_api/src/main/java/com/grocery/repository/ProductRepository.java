package com.grocery.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.grocery.dto.ProductItemDetails;
import com.grocery.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long>{
	
	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " + 
            "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " + 
            "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
            "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
            "FROM product p " +
            "JOIN product_item pi ON p.product_id = pi.product_id " + 
            "JOIN unit u ON u.unit_id = pi.unit_id " +
            "JOIN product_category pc ON pc.category_id = p.category_id", nativeQuery = true)
	List<ProductItemDetails> findProductDetails();

	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " + 
            "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " + 
            "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
            "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
            "FROM product p " +
            "JOIN product_item pi ON p.product_id = pi.product_id " + 
            "JOIN unit u ON u.unit_id = pi.unit_id " +
            "JOIN product_category pc ON pc.category_id = p.category_id " +
            "WHERE pi.product_item_id = :productItemId", nativeQuery = true)
	Optional<ProductItemDetails> findProductDetailByProductItemId(@Param("productItemId") Long productItemId);

	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " + 
            "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " + 
            "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
            "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
            "FROM product p " +
            "JOIN product_item pi ON p.product_id = pi.product_id " + 
            "JOIN unit u ON u.unit_id = pi.unit_id " +
            "JOIN product_category pc ON pc.category_id = p.category_id " + 
            "WHERE LOWER(pc.name) = :category", nativeQuery = true)
	List<ProductItemDetails> findByCategory(@Param("category") String category);

	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " + 
            "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " + 
            "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
            "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
            "FROM product p " +
            "JOIN product_item pi ON p.product_id = pi.product_id " + 
            "JOIN unit u ON u.unit_id = pi.unit_id " +
            "JOIN product_category pc ON pc.category_id = p.category_id " + 
            "WHERE LOWER(p.prod_name) LIKE %:query% OR LOWER(p.brand) LIKE %:query% OR LOWER(pc.name) LIKE %:query%", nativeQuery=true)
	List<ProductItemDetails> searchProduct(@Param("query") String query);

	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " + 
            "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " + 
            "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
            "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
            "FROM product p " +
            "JOIN product_item pi ON p.product_id = pi.product_id " + 
            "JOIN unit u ON u.unit_id = pi.unit_id " +
            "JOIN product_category pc ON pc.category_id = p.category_id " + 
            "ORDER BY discountPercentage DESC LIMIT 5", nativeQuery = true)
	List<ProductItemDetails> displayTopDiscounts();

	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " + 
            "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " + 
            "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
            "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
            "FROM product p " +
            "JOIN product_item pi ON p.product_id = pi.product_id " + 
            "JOIN unit u ON u.unit_id = pi.unit_id " +
            "JOIN product_category pc ON pc.category_id = p.category_id " + 
            "WHERE p.product_id = :prodId", nativeQuery = true)
	List<ProductItemDetails> displayProductsById(@Param("prodId") Long productId);

	
	@Query(value = "SELECT pc.name AS categoryName, p.product_id AS productId, p.prod_name AS prodName, " +
	        "p.product_img AS productImg, p.brand, p.description, pi.product_item_id AS productItemId, " +
	        "pi.price, pi.sale_price AS salePrice, ((pi.price - pi.sale_price) / pi.price * 100) AS discountPercentage, " +
	        "pi.qty_in_stock AS quantityInStock, u.unit_id AS unitId, u.unit_name AS unitName " +
	        "FROM product p " +
	        "JOIN product_item pi ON p.product_id = pi.product_id " +
	        "JOIN unit u ON u.unit_id = pi.unit_id " +
	        "JOIN product_category pc ON pc.category_id = p.category_id " +
	        "WHERE pi.product_item_id = :productItemId", nativeQuery = true)
	Optional<ProductItemDetails> findProductItemDetailsByProductItemId(@Param("productItemId") Long productId);

}

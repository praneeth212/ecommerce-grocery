package com.grocery.modeltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grocery.model.Category;
import com.grocery.model.Product;
import com.grocery.model.ProductItem;
import com.grocery.model.Units;

public class CategoryTests {

    private Category parentCategory;
    private Category category;
    private List<Product> products;

    @BeforeEach
    public void setUp() {
        parentCategory = new Category(1L, "Parent Category", "Description of parent category", "http://example.com/parent.jpg", null, new ArrayList<>());

        Units unit1 = new Units(1L, "250 Gms", new ArrayList<>());
        Units unit2 = new Units(2L, "500 Gms", new ArrayList<>());

        Product product1 = new Product(1L, "Product 1", "http://example.com/product1.jpg", "Description 1", "Brand 1", parentCategory, new ArrayList<>());
        Product product2 = new Product(2L, "Product 2", "http://example.com/product2.jpg", "Description 2", "Brand 2", parentCategory, new ArrayList<>());

        ProductItem productItem1 = new ProductItem(1L, 100.0, 90.0, 10, product1, unit1);
        ProductItem productItem2 = new ProductItem(2L, 200.0, 180.0, 20, product2, unit2);

        product1.getProductItems().add(productItem1);
        product2.getProductItems().add(productItem2);

        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        category = new Category(2L, "Category", "Description of category", "http://example.com/category.jpg", parentCategory, products);
    }

    @Test
    public void testConstructor() {
        assertEquals(2L, category.getId());
        assertEquals("Category", category.getName());
        assertEquals("Description of category", category.getDescription());
        assertEquals("http://example.com/category.jpg", category.getImageUrl());
        assertEquals(parentCategory, category.getParentCategory());
        assertEquals(products, category.getProducts());
    }

    @Test
    public void testGetAndSetParentCategory() {
        Category newParentCategory = new Category(3L, "New Parent Category", "Description of new parent category", "http://example.com/new_parent.jpg", null, new ArrayList<>());
        category.setParentCategory(newParentCategory);
        assertEquals(newParentCategory, category.getParentCategory());
    }

    @Test
    public void testGetAndSetProducts() {
        Units unit3 = new Units(3L, "750 Gms", new ArrayList<>());

        Product product3 = new Product(3L, "Product 3", "http://example.com/product3.jpg", "Description 3", "Brand 3", category, new ArrayList<>());
        ProductItem productItem3 = new ProductItem(3L, 300.0, 270.0, 30, product3, unit3);
        product3.getProductItems().add(productItem3);

        List<Product> newProducts = new ArrayList<>();
        newProducts.add(product3);

        category.setProducts(newProducts);
        assertEquals(newProducts, category.getProducts());
    }

    @Test
    public void testEmptyConstructor() {
        Category emptyCategory = new Category();
        assertNull(emptyCategory.getId());
        assertNull(emptyCategory.getName());
        assertNull(emptyCategory.getDescription());
        assertNull(emptyCategory.getImageUrl());
        assertNull(emptyCategory.getParentCategory());
        assertNull(emptyCategory.getProducts());
    }
}
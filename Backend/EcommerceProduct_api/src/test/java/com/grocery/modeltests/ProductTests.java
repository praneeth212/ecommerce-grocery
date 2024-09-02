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

public class ProductTests {

    private Product product;
    private Category category;
    private List<ProductItem> productItems;

    @BeforeEach
    public void setUp() {
        category = new Category(1L, "Category", "Description of category", "http://example.com/category.jpg", null, new ArrayList<>());

        Units unit1 = new Units(1L, "250 Gms", new ArrayList<>());
        Units unit2 = new Units(2L, "500 Gms", new ArrayList<>());

        ProductItem productItem1 = new ProductItem(1L, 100.0, 90.0, 10, product, unit1);
        ProductItem productItem2 = new ProductItem(2L, 200.0, 180.0, 20, product, unit2);

        productItems = new ArrayList<>();
        productItems.add(productItem1);
        productItems.add(productItem2);

        product = new Product(1L, "Product", "http://example.com/product.jpg", "Description of product", "Brand", category, productItems);
    }

    @Test
    public void testConstructor() {
        assertEquals(1L, product.getId());
        assertEquals("Product", product.getProdName());
        assertEquals("http://example.com/product.jpg", product.getImageUrl());
        assertEquals("Description of product", product.getDescription());
        assertEquals("Brand", product.getBrand());
        assertEquals(category, product.getCategory());
        assertEquals(productItems, product.getProductItems());
    }

    @Test
    public void testGetAndSetId() {
        product.setId(2L);
        assertEquals(2L, product.getId());
    }

    @Test
    public void testGetAndSetProdName() {
        product.setProdName("New Product Name");
        assertEquals("New Product Name", product.getProdName());
    }

    @Test
    public void testGetAndSetImageUrl() {
        product.setImageUrl("http://example.com/new_product.jpg");
        assertEquals("http://example.com/new_product.jpg", product.getImageUrl());
    }

    @Test
    public void testGetAndSetDescription() {
        product.setDescription("New Description");
        assertEquals("New Description", product.getDescription());
    }

    @Test
    public void testGetAndSetBrand() {
        product.setBrand("New Brand");
        assertEquals("New Brand", product.getBrand());
    }

    @Test
    public void testGetAndSetCategory() {
        Category newCategory = new Category(2L, "New Category", "Description of new category", "http://example.com/new_category.jpg", null, new ArrayList<>());
        product.setCategory(newCategory);
        assertEquals(newCategory, product.getCategory());
    }

    @Test
    public void testGetAndSetProductItems() {
        Units unit3 = new Units(3L, "750 Gms", new ArrayList<>());

        ProductItem productItem3 = new ProductItem(3L, 300.0, 270.0, 30, product, unit3);
        List<ProductItem> newProductItems = new ArrayList<>();
        newProductItems.add(productItem3);

        product.setProductItems(newProductItems);
        assertEquals(newProductItems, product.getProductItems());
    }

    @Test
    public void testEmptyConstructor() {
        Product emptyProduct = new Product();
        assertNull(emptyProduct.getId());
        assertNull(emptyProduct.getProdName());
        assertNull(emptyProduct.getImageUrl());
        assertNull(emptyProduct.getDescription());
        assertNull(emptyProduct.getBrand());
        assertNull(emptyProduct.getCategory());
        assertNull(emptyProduct.getProductItems());
    }
}

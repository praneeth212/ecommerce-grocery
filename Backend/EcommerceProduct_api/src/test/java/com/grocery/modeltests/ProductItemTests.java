package com.grocery.modeltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grocery.model.Product;
import com.grocery.model.ProductItem;
import com.grocery.model.Units;

public class ProductItemTests {

    private ProductItem productItem;
    private Product product;
    private Units unit;

    @BeforeEach
    public void setUp() {
        product = new Product(1L, "Product", "http://example.com/product.jpg", "Description of product", "Brand", null, null);
        unit = new Units(1L, "250 Gms", null);

        productItem = new ProductItem(1L, 100.0, 90.0, 10, product, unit);
    }

    @Test
    public void testConstructor() {
        assertEquals(1L, productItem.getId());
        assertEquals(100.0, productItem.getPrice());
        assertEquals(90.0, productItem.getSalePrice());
        assertEquals(10, productItem.getQuantityInStock());
        assertEquals(product, productItem.getProduct());
        assertEquals(unit, productItem.getUnit());
    }

    @Test
    public void testGetAndSetId() {
        productItem.setId(2L);
        assertEquals(2L, productItem.getId());
    }

    @Test
    public void testGetAndSetPrice() {
        productItem.setPrice(150.0);
        assertEquals(150.0, productItem.getPrice());
    }

    @Test
    public void testGetAndSetSalePrice() {
        productItem.setSalePrice(120.0);
        assertEquals(120.0, productItem.getSalePrice());
    }

    @Test
    public void testGetAndSetQuantityInStock() {
        productItem.setQuantityInStock(20);
        assertEquals(20, productItem.getQuantityInStock());
    }

    @Test
    public void testGetAndSetProduct() {
        Product newProduct = new Product(2L, "New Product", "http://example.com/new_product.jpg", "New description", "New Brand", null, null);
        productItem.setProduct(newProduct);
        assertEquals(newProduct, productItem.getProduct());
    }

    @Test
    public void testGetAndSetUnit() {
        Units newUnit = new Units(2L, "500 Gms", null);
        productItem.setUnit(newUnit);
        assertEquals(newUnit, productItem.getUnit());
    }

    @Test
    public void testEmptyConstructor() {
        ProductItem emptyProductItem = new ProductItem();
        assertNull(emptyProductItem.getId());
        assertNull(emptyProductItem.getPrice());
        assertNull(emptyProductItem.getSalePrice());
    }
}

package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemsTest {

    private OrderItems orderItem;
    private Orders order;
    private Product product;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItems();
        order = new Orders();
        product = new Product();
    }

    @Test
    public void testOrderItemId() {
        String orderItemId = "12345";
        orderItem.setOrderItemId(orderItemId);
        assertEquals(orderItemId, orderItem.getOrderItemId());
    }

    @Test
    public void testProductName() {
        String productName = "Product Name";
        orderItem.setProductName(productName);
        assertEquals(productName, orderItem.getProductName());
    }

    @Test
    public void testUnit() {
        String unit = "pcs";
        orderItem.setUnit(unit);
        assertEquals(unit, orderItem.getUnit());
    }

    @Test
    public void testQuantity() {
        String quantity = "10";
        orderItem.setQuantity(quantity);
        assertEquals(quantity, orderItem.getQuantity());
    }

    @Test
    public void testPrice() {
        Long price = 100L;
        orderItem.setPrice(price);
        assertEquals(price, orderItem.getPrice());
    }

    @Test
    public void testDiscountedPrice() {
        Long discountedPrice = 90L;
        orderItem.setDiscountedPrice(discountedPrice);
        assertEquals(discountedPrice, orderItem.getDiscountedPrice());
    }

    @Test
    public void testDeliveryDate() {
        Date deliveryDate = new Date(System.currentTimeMillis());
        orderItem.setDeliveryDate(deliveryDate);
        assertEquals(deliveryDate, orderItem.getDeliveryDate());
    }

    @Test
    public void testOrder() {
        orderItem.setOrder(order);
        assertEquals(order, orderItem.getOrder());
    }

    @Test
    public void testProduct() {
        orderItem.setProduct(product);
        assertEquals(product, orderItem.getProduct());
    }

    @Test
    public void testToString() {
        String orderItemId = "12345";
        orderItem.setOrderItemId(orderItemId);
        String productName = "Product Name";
        orderItem.setProductName(productName);
        String unit = "pcs";
        orderItem.setUnit(unit);
        String quantity = "10";
        orderItem.setQuantity(quantity);
        Long price = 100L;
        orderItem.setPrice(price);
        Long discountedPrice = 90L;
        orderItem.setDiscountedPrice(discountedPrice);
        Date deliveryDate = new Date(System.currentTimeMillis());
        orderItem.setDeliveryDate(deliveryDate);
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        String expectedString = "OrderItems [orderItemId=" + orderItemId + ", productName=" + productName
                + ", unit=" + unit + ", quantity=" + quantity + ", price=" + price + ", discountedPrice="
                + discountedPrice + ", deliveryDate=" + deliveryDate + ", order=" + order + ", product=" + product
                + "]";
        assertEquals(expectedString, orderItem.toString());
    }
}

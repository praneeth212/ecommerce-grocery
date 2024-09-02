package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemsHistoryTest {

    private OrderItemsHistory orderItemHistory;
    private OrdersHistory ordersHistory;
    private Product product;

    @BeforeEach
    public void setUp() {
        orderItemHistory = new OrderItemsHistory();
        ordersHistory = new OrdersHistory();
        product = new Product();
    }

    @Test
    public void testOrderItemHistId() {
        String orderItemHistId = "12345";
        orderItemHistory.setOrderItemHistId(orderItemHistId);
        assertEquals(orderItemHistId, orderItemHistory.getOrderItemHistId());
    }

    @Test
    public void testProductName() {
        String productName = "Product Name";
        orderItemHistory.setProductName(productName);
        assertEquals(productName, orderItemHistory.getProductName());
    }

    @Test
    public void testUnit() {
        String unit = "pcs";
        orderItemHistory.setUnit(unit);
        assertEquals(unit, orderItemHistory.getUnit());
    }

    @Test
    public void testQuantity() {
        String quantity = "10";
        orderItemHistory.setQuantity(quantity);
        assertEquals(quantity, orderItemHistory.getQuantity());
    }

    @Test
    public void testPrice() {
        Long price = 100L;
        orderItemHistory.setPrice(price);
        assertEquals(price, orderItemHistory.getPrice());
    }

    @Test
    public void testDiscountedPrice() {
        Long discountedPrice = 90L;
        orderItemHistory.setDiscountedPrice(discountedPrice);
        assertEquals(discountedPrice, orderItemHistory.getDiscountedPrice());
    }

    @Test
    public void testDeliveryDate() {
        Date deliveryDate = new Date(System.currentTimeMillis());
        orderItemHistory.setDeliveryDate(deliveryDate);
        assertEquals(deliveryDate, orderItemHistory.getDeliveryDate());
    }

    @Test
    public void testOrderHistory() {
        orderItemHistory.setOrderHistory(ordersHistory);
        assertEquals(ordersHistory, orderItemHistory.getOrderHistory());
    }

    @Test
    public void testProduct() {
        orderItemHistory.setProduct(product);
        assertEquals(product, orderItemHistory.getProduct());
    }

    @Test
    public void testToString() {
        String orderItemHistId = "12345";
        orderItemHistory.setOrderItemHistId(orderItemHistId);
        String productName = "Product Name";
        orderItemHistory.setProductName(productName);
        String unit = "pcs";
        orderItemHistory.setUnit(unit);
        String quantity = "10";
        orderItemHistory.setQuantity(quantity);
        Long price = 100L;
        orderItemHistory.setPrice(price);
        Long discountedPrice = 90L;
        orderItemHistory.setDiscountedPrice(discountedPrice);
        Date deliveryDate = new Date(System.currentTimeMillis());
        orderItemHistory.setDeliveryDate(deliveryDate);
        orderItemHistory.setOrderHistory(ordersHistory);
        orderItemHistory.setProduct(product);

        String expectedString = "OrderItemsHistory [orderItemHistId=" + orderItemHistId + ", productName="
                + productName + ", unit=" + unit + ", quantity=" + quantity + ", price=" + price + ", discountedPrice="
                + discountedPrice + ", deliveryDate=" + deliveryDate + ", orderHistory=" + ordersHistory + ", product="
                + product + "]";
        assertEquals(expectedString, orderItemHistory.toString());
    }
}

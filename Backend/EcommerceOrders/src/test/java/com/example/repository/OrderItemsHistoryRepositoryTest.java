package com.example.repository;

import com.example.model.OrderItemsHistory;
import com.example.model.OrdersHistory;
import com.example.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderItemsHistoryRepositoryTest {

    @Autowired
    private OrderItemsHistoryRepository orderItemsHistoryRepository;

    private OrdersHistory ordersHistory;
    private Product product;

    @BeforeEach
    public void setUp() {
        ordersHistory = new OrdersHistory();
        ordersHistory.setOrderHistId("1");
        ordersHistory.setOrderDate(new Date(System.currentTimeMillis()));
        ordersHistory.setStatus("DELIVERED");

   
    }

    @Test
    public void testSaveAndFindById() {
        OrderItemsHistory orderItemHistory = new OrderItemsHistory();
        orderItemHistory.setOrderItemHistId("1");
        orderItemHistory.setOrderHistory(ordersHistory);
        orderItemHistory.setProduct(product);
        orderItemHistory.setProductName("Product 1");
        orderItemHistory.setUnit("pcs");
        orderItemHistory.setQuantity("10");
        orderItemHistory.setPrice(100L);
        orderItemHistory.setDiscountedPrice(90L);
        orderItemHistory.setDeliveryDate(new Date(System.currentTimeMillis()));

        orderItemsHistoryRepository.save(orderItemHistory);

        Optional<OrderItemsHistory> foundOrderItemHistory = orderItemsHistoryRepository.findById("1");
        assertTrue(foundOrderItemHistory.isPresent());
        assertEquals("Product 1", foundOrderItemHistory.get().getProductName());
    }

    @Test
    public void testUpdate() {
        OrderItemsHistory orderItemHistory = new OrderItemsHistory();
        orderItemHistory.setOrderItemHistId("1");
        orderItemHistory.setOrderHistory(ordersHistory);
        orderItemHistory.setProduct(product);
        orderItemHistory.setProductName("Product 1");
        orderItemHistory.setUnit("pcs");
        orderItemHistory.setQuantity("10");
        orderItemHistory.setPrice(100L);
        orderItemHistory.setDiscountedPrice(90L);
        orderItemHistory.setDeliveryDate(new Date(System.currentTimeMillis()));

        orderItemsHistoryRepository.save(orderItemHistory);

        OrderItemsHistory savedOrderItemHistory = orderItemsHistoryRepository.findById("1").orElseThrow();
        savedOrderItemHistory.setQuantity("20");
        orderItemsHistoryRepository.save(savedOrderItemHistory);

        OrderItemsHistory updatedOrderItemHistory = orderItemsHistoryRepository.findById("1").orElseThrow();
        assertEquals("20", updatedOrderItemHistory.getQuantity());
    }

    @Test
    public void testDelete() {
        OrderItemsHistory orderItemHistory = new OrderItemsHistory();
        orderItemHistory.setOrderItemHistId("1");
        orderItemHistory.setOrderHistory(ordersHistory);
        orderItemHistory.setProduct(product);
        orderItemHistory.setProductName("Product 1");
        orderItemHistory.setUnit("pcs");
        orderItemHistory.setQuantity("10");
        orderItemHistory.setPrice(100L);
        orderItemHistory.setDiscountedPrice(90L);
        orderItemHistory.setDeliveryDate(new Date(System.currentTimeMillis()));

        orderItemsHistoryRepository.save(orderItemHistory);
        orderItemsHistoryRepository.deleteById("1");

        Optional<OrderItemsHistory> deletedOrderItemHistory = orderItemsHistoryRepository.findById("1");
        assertTrue(deletedOrderItemHistory.isEmpty());
    }
}

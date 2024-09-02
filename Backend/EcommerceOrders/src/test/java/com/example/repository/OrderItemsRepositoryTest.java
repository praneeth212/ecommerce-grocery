package com.example.repository;

import com.example.model.OrderItems;
import com.example.model.Orders;
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
public class OrderItemsRepositoryTest {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    private Orders order;
    private Product product;

    @BeforeEach
    public void setUp() {
        order = new Orders();
        order.setOrderId("1");
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setStatus("PENDING");

    }

    @Test
    public void testSaveAndFindById() {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderItemId("1");
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductName("Product 1");
        orderItem.setUnit("pcs");
        orderItem.setQuantity("10");
        orderItem.setPrice(100L);
        orderItem.setDiscountedPrice(90L);
        orderItem.setDeliveryDate(new Date(System.currentTimeMillis()));

        orderItemsRepository.save(orderItem);

        Optional<OrderItems> foundOrderItem = orderItemsRepository.findById("1");
        assertTrue(foundOrderItem.isPresent());
        assertEquals("Product 1", foundOrderItem.get().getProductName());
    }

    @Test
    public void testUpdate() {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderItemId("1");
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductName("Product 1");
        orderItem.setUnit("pcs");
        orderItem.setQuantity("10");
        orderItem.setPrice(100L);
        orderItem.setDiscountedPrice(90L);
        orderItem.setDeliveryDate(new Date(System.currentTimeMillis()));

        orderItemsRepository.save(orderItem);

        OrderItems savedOrderItem = orderItemsRepository.findById("1").orElseThrow();
        savedOrderItem.setQuantity("20");
        orderItemsRepository.save(savedOrderItem);

        OrderItems updatedOrderItem = orderItemsRepository.findById("1").orElseThrow();
        assertEquals("20", updatedOrderItem.getQuantity());
    }

    @Test
    public void testDelete() {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrderItemId("1");
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductName("Product 1");
        orderItem.setUnit("pcs");
        orderItem.setQuantity("10");
        orderItem.setPrice(100L);
        orderItem.setDiscountedPrice(90L);
        orderItem.setDeliveryDate(new Date(System.currentTimeMillis()));

        orderItemsRepository.save(orderItem);
        orderItemsRepository.deleteById("1");

        Optional<OrderItems> deletedOrderItem = orderItemsRepository.findById("1");
        assertTrue(deletedOrderItem.isEmpty());
    }
}

//package com.example.repository;
//
//import com.example.model.Address;
//import com.example.model.Orders;
//import com.example.model.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.sql.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//public class OrdersRepositoryTest {
//
//    @Autowired
//    private OrdersRepository ordersRepository;
//
//    private User user;
//    private Address address;
//
//    @BeforeEach
//    public void setUp() {
//
//        Orders order1 = new Orders();
//        order1.setOrderId("1");
//        order1.setUser(user);
//        order1.setAddress(address);
//        order1.setStatus("DELIVERED");
//        order1.setOrderDate(new Date(System.currentTimeMillis()));
//        order1.setTotalPrice(100L);
//        order1.setTotalItem("5");
//        order1.setDiscount(10L);
//
//        Orders order2 = new Orders();
//        order2.setOrderId("2");
//        order2.setUser(user);
//        order2.setAddress(address);
//        order2.setStatus("PENDING");
//        order2.setOrderDate(new Date(System.currentTimeMillis()));
//        order2.setTotalPrice(200L);
//        order2.setTotalItem("10");
//        order2.setDiscount(20L);
//
//        ordersRepository.save(order1);
//        ordersRepository.save(order2);
//    }
//
//    @Test
//    public void testFindByUserId() {
//        List<Orders> orders = ordersRepository.findByUserId("1");
//        assertNotNull(orders);
//        assertEquals(2, orders.size());
//    }
//
//    @Test
//    public void testFindByStatus() {
//        List<Orders> deliveredOrders = ordersRepository.findByStatus("DELIVERED");
//        assertNotNull(deliveredOrders);
//        assertEquals(1, deliveredOrders.size());
//        assertEquals("DELIVERED", deliveredOrders.get(0).getStatus());
//
//        List<Orders> pendingOrders = ordersRepository.findByStatus("PENDING");
//        assertNotNull(pendingOrders);
//        assertEquals(1, pendingOrders.size());
//        assertEquals("PENDING", pendingOrders.get(0).getStatus());
//    }
//}

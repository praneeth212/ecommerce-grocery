//package com.example.repository;
//
//import com.example.model.Address;
//import com.example.model.OrdersHistory;
//import com.example.model.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.sql.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//public class OrdersHistoryRepositoryTest {
//
//    @Autowired
//    private OrdersHistoryRepository ordersHistoryRepository;
//
//    private User user;
//    private Address address;
//
//    @BeforeEach
//    public void setUp() {
//
//        OrdersHistory order1 = new OrdersHistory();
//        order1.setOrderHistId("1");
//        order1.setUser(user);
//        order1.setAddress(address);
//        order1.setStatus("DELIVERED");
//        order1.setOrderDate(new Date(System.currentTimeMillis()));
//        order1.setTotalPrice(100L);
//        order1.setTotalItem("5");
//        order1.setDiscount(10L);
//
//        OrdersHistory order2 = new OrdersHistory();
//        order2.setOrderHistId("2");
//        order2.setUser(user);
//        order2.setAddress(address);
//        order2.setStatus("PENDING");
//        order2.setOrderDate(new Date(System.currentTimeMillis()));
//        order2.setTotalPrice(200L);
//        order2.setTotalItem("10");
//        order2.setDiscount(20L);
//
//        ordersHistoryRepository.save(order1);
//        ordersHistoryRepository.save(order2);
//    }
//
//    @Test
//    public void testFindByUserId() {
//        List<OrdersHistory> orders = ordersHistoryRepository.findByUserId("1");
//        assertNotNull(orders);
//        assertEquals(2, orders.size());
//    }
//
//    @Test
//    public void testFindByStatus() {
//        List<OrdersHistory> deliveredOrders = ordersHistoryRepository.findByStatus("DELIVERED");
//        assertNotNull(deliveredOrders);
//        assertEquals(1, deliveredOrders.size());
//        assertEquals("DELIVERED", deliveredOrders.get(0).getStatus());
//
//        List<OrdersHistory> pendingOrders = ordersHistoryRepository.findByStatus("PENDING");
//        assertNotNull(pendingOrders);
//        assertEquals(1, pendingOrders.size());
//        assertEquals("PENDING", pendingOrders.get(0).getStatus());
//    }
//}

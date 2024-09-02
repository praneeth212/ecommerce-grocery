//package com.example.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class OrdersHistoryTest {
//
//    private OrdersHistory ordersHistory;
//    private User user;
//    private Address address;
//    private List<OrderItemsHistory> items;
//
//    @BeforeEach
//    public void setUp() {
//        ordersHistory = new OrdersHistory();
//        user = new User();
//        address = new Address();
//        items = new ArrayList<>();
//    }
//
//    @Test
//    public void testOrderHistId() {
//        String orderHistId = "12345";
//        ordersHistory.setOrderHistId(orderHistId);
//        assertEquals(orderHistId, ordersHistory.getOrderHistId());
//    }
//
//    @Test
//    public void testDiscount() {
//        Long discount = 10L;
//        ordersHistory.setDiscount(discount);
//        assertEquals(discount, ordersHistory.getDiscount());
//    }
//
//    @Test
//    public void testTotalPrice() {
//        Long totalPrice = 100L;
//        ordersHistory.setTotalPrice(totalPrice);
//        assertEquals(totalPrice, ordersHistory.getTotalPrice());
//    }
//
//    @Test
//    public void testOrderDate() {
//        Date orderDate = new Date(System.currentTimeMillis());
//        ordersHistory.setOrderDate(orderDate);
//        assertEquals(orderDate, ordersHistory.getOrderDate());
//    }
//
//    @Test
//    public void testTotalItem() {
//        String totalItem = "5";
//        ordersHistory.setTotalItem(totalItem);
//        assertEquals(totalItem, ordersHistory.getTotalItem());
//    }
//
//    @Test
//    public void testStatus() {
//        String status = "DELIVERED";
//        ordersHistory.setStatus(status);
//        assertEquals(status, ordersHistory.getStatus());
//    }
//
//    @Test
//    public void testAddress() {
//        ordersHistory.setAddress(address);
//        assertEquals(address, ordersHistory.getAddress());
//    }
//
//    @Test
//    public void testItems() {
//        OrderItemsHistory item = new OrderItemsHistory();
//        items.add(item);
//        ordersHistory.setItems(items);
//        assertEquals(items, ordersHistory.getItems());
//    }
//
//    @Test
//    public void testUser() {
//        ordersHistory.setUser(user);
//        assertEquals(user, ordersHistory.getUser());
//    }
//
//    @Test
//    public void testToString() {
//        String orderHistId = "12345";
//        ordersHistory.setOrderHistId(orderHistId);
//        Long discount = 10L;
//        ordersHistory.setDiscount(discount);
//        Long totalPrice = 100L;
//        ordersHistory.setTotalPrice(totalPrice);
//        Date orderDate = new Date(System.currentTimeMillis());
//        ordersHistory.setOrderDate(orderDate);
//        String totalItem = "5";
//        ordersHistory.setTotalItem(totalItem);
//        String status = "DELIVERED";
//        ordersHistory.setStatus(status);
//        ordersHistory.setAddress(address);
//        OrderItemsHistory item = new OrderItemsHistory();
//        items.add(item);
//        ordersHistory.setItems(items);
//        ordersHistory.setUser(user);
//
//        String expectedString = "OrdersHistory [orderHistId=" + orderHistId + ", discount=" + discount + ","
//                + " totalPrice=" + totalPrice + ", orderDate=" + orderDate + ", totalItem=" + totalItem + ", status="
//                + status + ", address=" + address + ", items=" + items + ", user=" + user + "]";
//        assertEquals(expectedString, ordersHistory.toString());
//    }
//}

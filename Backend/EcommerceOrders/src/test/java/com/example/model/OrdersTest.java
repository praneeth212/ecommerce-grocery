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
//public class OrdersTest {
//
//    private Orders order;
//    private User user;
//    private Address address;
//    private List<OrderItems> items;
//    private Cart cart;
//
//    @BeforeEach
//    public void setUp() {
//        order = new Orders();
//        user = new User();
//        address = new Address();
//        items = new ArrayList<>();
//        cart = new Cart();
//    }
//
//    @Test
//    public void testOrderId() {
//        String orderId = "12345";
//        order.setOrderId(orderId);
//        assertEquals(orderId, order.getOrderId());
//    }
//
//    @Test
//    public void testDiscount() {
//        Long discount = 10L;
//        order.setDiscount(discount);
//        assertEquals(discount, order.getDiscount());
//    }
//
//    @Test
//    public void testAddressId() {
//        String addressId = "67890";
//        order.setAddressId(addressId);
//        assertEquals(addressId, order.getAddressId());
//    }
//
//    @Test
//    public void testTotalPrice() {
//        Long totalPrice = 100L;
//        order.setTotalPrice(totalPrice);
//        assertEquals(totalPrice, order.getTotalPrice());
//    }
//
//    @Test
//    public void testOrderDate() {
//        Date orderDate = new Date(System.currentTimeMillis());
//        order.setOrderDate(orderDate);
//        assertEquals(orderDate, order.getOrderDate());
//    }
//
//    @Test
//    public void testTotalItem() {
//        String totalItem = "5";
//        order.setTotalItem(totalItem);
//        assertEquals(totalItem, order.getTotalItem());
//    }
//
//    @Test
//    public void testStatus() {
//        String status = "PENDING";
//        order.setStatus(status);
//        assertEquals(status, order.getStatus());
//    }
//
//    @Test
//    public void testAddress() {
//        order.setAddress(address);
//        assertEquals(address, order.getAddress());
//    }
//
//    @Test
//    public void testCart() {
//        order.setCart(cart);
//        assertEquals(cart, order.getCart());
//    }
//
//    @Test
//    public void testItems() {
//        OrderItems item = new OrderItems();
//        items.add(item);
//        order.setItems(items);
//        assertEquals(items, order.getItems());
//    }
//
//    @Test
//    public void testUser() {
//        order.setUser(user);
//        assertEquals(user, order.getUser());
//    }
//
//    @Test
//    public void testToString() {
//        String orderId = "12345";
//        order.setOrderId(orderId);
//        Long discount = 10L;
//        order.setDiscount(discount);
//        String addressId = "67890";
//        order.setAddressId(addressId);
//        Long totalPrice = 100L;
//        order.setTotalPrice(totalPrice);
//        Date orderDate = new Date(System.currentTimeMillis());
//        order.setOrderDate(orderDate);
//        String totalItem = "5";
//        order.setTotalItem(totalItem);
//        String status = "PENDING";
//        order.setStatus(status);
//        order.setAddress(address);
//        order.setCart(cart);
//        order.setItems(items);
//        order.setUser(user);
//
//        String expectedString = "Orders [orderId=" + orderId + ", discount=" + discount + ", addressId=" + addressId + ", totalPrice="
//                + totalPrice + ", orderDate=" + orderDate + ", totalItem=" + totalItem + ", status=" + status
//                + ", address=" + address + ", cart=" + cart + ", items=" + items + ", user=" + user + "]";
//        assertEquals(expectedString, order.toString());
//    }
//}

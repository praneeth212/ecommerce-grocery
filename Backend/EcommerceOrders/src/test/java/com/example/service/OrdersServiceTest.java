//package com.example.service;
//
//import com.example.model.*;
//import com.example.repository.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//public class OrdersServiceTest {
//
//    @Mock
//    private OrdersRepository ordersRepository;
//
//    @Mock
//    private OrderItemsRepository orderItemsRepository;
//
//    @Mock
//    private OrdersHistoryRepository ordersHistoryRepository;
//
//    @Mock
//    private OrderItemsHistoryRepository orderItemsHistoryRepository;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private OrdersService ordersService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testGetOrdersByUserId() {
//        String userId = "1";
//        Orders order = new Orders();
//        OrderItems item = new OrderItems();
//        order.setItems(Arrays.asList(item));
//        List<Orders> ordersList = Arrays.asList(order);
//
//        when(ordersRepository.findByUserId(userId)).thenReturn(ordersList);
//        when(restTemplate.getForObject(anyString(), eq(Product.class))).thenReturn(new Product());
//        when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(new Address());
//
//        List<Orders> result = ordersService.getOrdersByUserId(userId);
//
//        assertEquals(1, result.size());
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Product.class));
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Address.class));
//    }
//
//    @Test
//    public void testGetOrdersHistoryByUserId() {
//        String userId = "1";
//        OrdersHistory orderHistory = new OrdersHistory();
//        OrderItemsHistory itemHistory = new OrderItemsHistory();
//        orderHistory.setItems(Arrays.asList(itemHistory));
//        List<OrdersHistory> ordersHistoryList = Arrays.asList(orderHistory);
//
//        when(ordersHistoryRepository.findByUserId(userId)).thenReturn(ordersHistoryList);
//        when(restTemplate.getForObject(anyString(), eq(Product.class))).thenReturn(new Product());
//        when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(new Address());
//
//        List<OrdersHistory> result = ordersService.getOrdersHistoryByUserId(userId);
//
//        assertEquals(1, result.size());
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Product.class));
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Address.class));
//    }
//
//    @Test
//    public void testSaveOrders() {
//        Orders order = new Orders();
//        OrderItems item = new OrderItems();
//        order.setItems(Arrays.asList(item));
//
//        String orderId = ordersService.saveOrders(order, true);
//
//        assertNotNull(orderId);
//        verify(ordersRepository, times(1)).save(order);
//    }
//
//    @Test
//    public void testSaveOrdersHistory() {
//        Orders order = new Orders();
//        order.setOrderId("1");
//        order.setItems(Arrays.asList(new OrderItems()));
//
//        ordersService.saveOrdersHistory(order);
//
//        verify(ordersHistoryRepository, times(1)).save(any(OrdersHistory.class));
//    }
//
//    @Test
//    public void testGetOrdersByStatus() {
//        String status = "Pending";
//        Orders order = new Orders();
//        OrderItems item = new OrderItems();
//        order.setItems(Arrays.asList(item));
//        List<Orders> ordersList = Arrays.asList(order);
//
//        when(ordersRepository.findByStatus(status)).thenReturn(ordersList);
//        when(restTemplate.getForObject(anyString(), eq(Product.class))).thenReturn(new Product());
//        when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(new Address());
//
//        List<Orders> result = ordersService.getOrdersByStatus(status);
//
//        assertEquals(1, result.size());
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Product.class));
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Address.class));
//    }
//
//    @Test
//    public void testGetOrderById() {
//        String orderId = "1";
//        Orders order = new Orders();
//        OrderItems item = new OrderItems();
//        order.setItems(Arrays.asList(item));
//
//        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(restTemplate.getForObject(anyString(), eq(Product.class))).thenReturn(new Product());
//        when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(new Address());
//
//        Orders result = ordersService.getOrderById(orderId);
//
//        assertNotNull(result);
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Product.class));
//        verify(restTemplate, times(1)).getForObject(anyString(), eq(Address.class));
//    }

//    @Test
//    public void testChangeOrderStatus() {
//        String orderId = "1";
//        String status = "delivered";
//        Orders order = new Orders();
//        order.setOrderId(orderId);
//
//        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));
//
//        ordersService.changeOrderStatus(orderId, status);
//
//        verify(ordersRepository, times(1)).delete(order);
//        verify(ordersHistoryRepository, times(1)).save(any(OrdersHistory.class));
//    }

//    @Test
//    public void testGetAddressByUserId() {
//        Long userId = 1L;
//        Address address = new Address();
//        List<Address> addresses = Arrays.asList(address, address);
//
//        when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(address);
//
//        List<Address> result = ordersService.getAddressByUserId(userId);
//
//        assertEquals(2, result.size());
//    }

//    @Test
//    public void testAddAddressByUserId() {
//        Long userId = 1L;
//        Address address = new Address();
//        List<Address> addresses = Arrays.asList(address, address);
//
//        when(restTemplate.postForObject(anyString(), any(Address.class), eq(Address.class))).thenReturn(address);
//        when(restTemplate.getForObject(anyString(), eq(Address.class))).thenReturn(address);
//
//        List<Address> result = ordersService.addAddressByUserId(address, userId);
//
//        assertEquals(2, result.size());
//    }
//}

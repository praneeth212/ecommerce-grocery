//package com.example.controller;
//
//import com.example.model.Address;
//import com.example.model.Orders;
//import com.example.model.OrdersHistory;
//import com.example.service.OrdersService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import java.util.Arrays;
//import java.util.List;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class OrdersControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private OrdersService ordersService;
//
//    @InjectMocks
//    private OrdersController ordersController;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(ordersController).build();
//    }
//
//    @Test
//    public void testCreateOrderHandler() throws Exception {
//        Orders order = new Orders();
//        when(ordersService.saveOrders(any(Orders.class), eq(true))).thenReturn("Order Created");
//
//        mockMvc.perform(post("/orders")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(order)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Order Created"));
//    }
//
//    @Test
//    public void testUserOrderHistoryHandler() throws Exception {
//        List<Orders> ordersList = Arrays.asList(new Orders(), new Orders());
//        when(ordersService.getOrdersByUserId("1")).thenReturn(ordersList);
//
//        mockMvc.perform(get("/orders/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testUserOrderHistory() throws Exception {
//        List<OrdersHistory> ordersHistoryList = Arrays.asList(new OrdersHistory(), new OrdersHistory());
//        when(ordersService.getOrdersHistoryByUserId("1")).thenReturn(ordersHistoryList);
//
//        mockMvc.perform(get("/ordersHistory/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testFindOrderHistorHandler() throws Exception {
//        Orders order = new Orders();
//        when(ordersService.getOrderById("1")).thenReturn(order);
//
//        mockMvc.perform(get("/orders/1"))
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$.id").value(order.getOrderId()));
//    }
//
//    @Test
//    public void testOrdersByStatusHandler() throws Exception {
//        List<Orders> ordersList = Arrays.asList(new Orders(), new Orders());
//        when(ordersService.getOrdersByStatus("Pending")).thenReturn(ordersList);
//
//        mockMvc.perform(get("/orders/status/Pending"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testUpdateOrderStatus() throws Exception {
//        mockMvc.perform(put("/orders/changeStatus/1/Delivered"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetAddressesByUserId() throws Exception {
//        List<Address> addresses = Arrays.asList(new Address(), new Address());
//        when(ordersService.getAddressByUserId(1L)).thenReturn(addresses);
//
//        mockMvc.perform(get("/orders/address/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testAddAddressesByUserId() throws Exception {
//        Address address = new Address();
//        List<Address> addresses = Arrays.asList(address, address);
//        when(ordersService.addAddressByUserId(any(Address.class), eq(1L))).thenReturn(addresses);
//
//        mockMvc.perform(post("/orders/address/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(address)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//    
//}
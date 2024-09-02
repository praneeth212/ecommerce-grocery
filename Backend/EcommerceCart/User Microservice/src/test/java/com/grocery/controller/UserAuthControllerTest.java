package com.grocery.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.grocery.modal.Address;
import com.grocery.service.AddressService;
import com.grocery.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserAuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private UserAuthController userAuthController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userAuthController).build();
    }

    @Test
    public void testAddAddress() throws Exception {
        Address address = new Address();
        address.setId(1L);
        when(addressService.saveAddress(any(Long.class), any(Address.class))).thenReturn(address);

        mockMvc.perform(post("/api/users/{userId}/addresses", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testGetUserAddresses() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get("/api/users/{userId}/addresses", userId))
                .andExpect(status().isOk());

        verify(addressService).getAddressesByUserId(userId);
    }

    @Test
    public void testSetDefaultAddress() throws Exception {
        Long userId = 1L;
        Long addressId = 1L;
        mockMvc.perform(put("/api/users/{userId}/addresses/{addressId}/default", userId, addressId))
                .andExpect(status().isOk());

        verify(addressService).setDefaultAddress(userId, addressId);
    }

    @Test
    public void testDeleteAddress() throws Exception {
        Long userId = 1L;
        Long addressId = 1L;
        mockMvc.perform(delete("/api/users/{userId}/addresses/{addressId}", userId, addressId))
                .andExpect(status().isNoContent());

        verify(addressService).deleteAddress(userId, addressId);
    }

}

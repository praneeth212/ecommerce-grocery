package com.grocery.service;

import com.grocery.modal.Address;
import com.grocery.modal.User;
import com.grocery.repository.AddressRepository;
import com.grocery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testSaveAddress() {
        Long userId = 1L;
        Address address = new Address();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(addressRepository.save(address)).thenReturn(address);

        Address savedAddress = addressService.saveAddress(userId, address);

        assertNotNull(savedAddress);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
     void testGetAddressesByUserId() {
        Long userId = 1L;
        List<Address> addresses = Collections.singletonList(new Address());

        when(addressRepository.findByUserId(userId)).thenReturn(addresses);

        List<Address> result = addressService.getAddressesByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(addressRepository, times(1)).findByUserId(userId);
    }

    @Test
     void testSetDefaultAddress() {
        Long userId = 1L;
        Long addressId = 1L;
        Address address = new Address();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);

        List<Address> updatedAddresses = addressService.setDefaultAddress(userId, addressId);

        assertNotNull(updatedAddresses);
        assertTrue(address.getDefault_address());
        verify(addressRepository, times(1)).updateDefaultAddressToFalse(userId);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
     void testDeleteAddress() {
        Long userId = 1L;
        Long addressId = 1L;
        Address address = new Address();
        User user = new User();
        user.setId(userId);
        address.setUser(user);

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        addressService.deleteAddress(userId, addressId);

        verify(addressRepository, times(1)).delete(address);
    }
}

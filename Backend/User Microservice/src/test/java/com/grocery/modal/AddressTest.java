package com.grocery.modal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressTest {

    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address();
    }

    @Test
    public void testSetAndGetId() {
        Long id = 1L;
        address.setId(id);
        assertEquals(id, address.getId());
    }

    @Test
    public void testSetAndGetAddressLine1() {
        String addressLine1 = "123 Main St";
        address.setAddress_line1(addressLine1);
        assertEquals(addressLine1, address.getAddress_line1());
    }

    @Test
    public void testSetAndGetAddressLine2() {
        String addressLine2 = "Apt 4";
        address.setAddress_line2(addressLine2);
        assertEquals(addressLine2, address.getAddress_line2());
    }

    @Test
    public void testSetAndGetCountry() {
        String country = "USA";
        address.setCountry(country);
        assertEquals(country, address.getCountry());
    }

    @Test
    public void testSetAndGetCity() {
        String city = "New York";
        address.setCity(city);
        assertEquals(city, address.getCity());
    }

    @Test
    public void testSetAndGetState() {
        String state = "NY";
        address.setState(state);
        assertEquals(state, address.getState());
    }

    @Test
    public void testSetAndGetZipcode() {
        String zipcode = "10001";
        address.setZipcode(zipcode);
        assertEquals(zipcode, address.getZipcode());
    }

    @Test
    public void testSetAndGetDefaultAddress() {
        Boolean defaultAddress = true;
        address.setDefault_address(defaultAddress);
        assertEquals(defaultAddress, address.getDefault_address());
    }

    @Test
    public void testSetAndGetUser() {
        User user = new User();
        user.setId(2L);
        user.setEmail("test@example.com");
        address.setUser(user);
        assertEquals(user, address.getUser());
        assertEquals(2L, address.getUser().getId());
    }
}

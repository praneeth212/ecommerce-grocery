package com.grocery.modal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.grocery.domain.USER_ROLE;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("securepassword123");
        user.setEmail("john.doe@example.com");
        user.setRole(USER_ROLE.ROLE_CUSTOMER);
        user.setMobile("1234567890");

        Address address = new Address();
        address.setId(1L);
        address.setAddress_line1("1234 Market St");
        address.setCity("San Francisco");
        address.setCountry("USA");
        address.setState("CA");
        address.setZipcode("94103");
        user.setAddresses(Arrays.asList(address));

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("securepassword123", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(USER_ROLE.ROLE_CUSTOMER, user.getRole());
        assertEquals("1234567890", user.getMobile());
        assertEquals(1, user.getAddresses().size());
        assertEquals("1234 Market St", user.getAddresses().get(0).getAddress_line1());
        assertEquals("San Francisco", user.getAddresses().get(0).getCity());
    }
}

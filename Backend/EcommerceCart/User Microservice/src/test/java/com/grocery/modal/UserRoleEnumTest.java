package com.grocery.modal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.grocery.domain.USER_ROLE;

public class UserRoleEnumTest {

    @Test
    public void testUserRole_ContainsAdminRole() {
        // Check if the enum contains ROLE_ADMIN
        assertTrue(Enum.valueOf(USER_ROLE.class, "ROLE_ADMIN") == USER_ROLE.ROLE_ADMIN);
    }

    @Test
    public void testUserRole_ContainsCustomerRole() {
        // Check if the enum contains ROLE_CUSTOMER
        assertTrue(Enum.valueOf(USER_ROLE.class, "ROLE_CUSTOMER") == USER_ROLE.ROLE_CUSTOMER);
    }

    @Test
    public void testUserRole_EnumSize() {
        // Optionally check the number of items in the enum
        assertTrue(USER_ROLE.values().length == 2);
    }
}

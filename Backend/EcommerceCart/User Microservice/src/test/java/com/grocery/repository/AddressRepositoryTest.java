package com.grocery.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.grocery.modal.Address;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AddressRepositoryTest {

    @Mock
    private AddressRepository addressRepository;

    private Address address1;
    private Address address2;

    @BeforeEach
    void setUp() {
        address1 = new Address();
        address1.setId(1L);
        address1.setDefault_address(true);

        address2 = new Address();
        address2.setId(2L);
        address2.setDefault_address(false);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        when(addressRepository.findByUserId(userId)).thenReturn(Arrays.asList(address1, address2));

        List<Address> addresses = addressRepository.findByUserId(userId);

        assertNotNull(addresses);
        assertEquals(2, addresses.size());
        assertEquals(address1, addresses.get(0));
        assertEquals(address2, addresses.get(1));
    }

    @Test
    void testUpdateDefaultAddressToFalse() {
        Long userId = 1L;

        addressRepository.updateDefaultAddressToFalse(userId);

        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(addressRepository).updateDefaultAddressToFalse(userIdCaptor.capture());

        assertEquals(userId, userIdCaptor.getValue());
    }

    @Test
    void testFindByUserIdAndDefaultAddressTrue() {
        Long userId = 1L;
        when(addressRepository.findByUserIdAndDefaultAddressTrue(userId)).thenReturn(address1);

        Address address = addressRepository.findByUserIdAndDefaultAddressTrue(userId);

        assertNotNull(address);
        assertEquals(address1, address);
    }
}

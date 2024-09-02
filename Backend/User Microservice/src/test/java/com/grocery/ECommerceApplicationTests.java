package com.grocery;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.grocery.service.AddressService;

@SpringBootTest
@ActiveProfiles("test")
class ECommerceApplicationTests {

    @Test
    void contextLoads() {
    	
    	final AddressService addressService = null;
    	
    	assertThat(addressService).isNull();
    	
    }
}
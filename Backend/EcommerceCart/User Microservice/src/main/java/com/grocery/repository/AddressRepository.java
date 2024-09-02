package com.grocery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grocery.modal.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Modifying
    @Query("UPDATE Address a SET a.default_address = false WHERE a.user.id = :userId")
    void updateDefaultAddressToFalse(@Param("userId") Long userId);
	
	List<Address> findByUserId(Long userId);
	
    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.default_address = true")
	Address findByUserIdAndDefaultAddressTrue(@Param("userId") Long userId);
}

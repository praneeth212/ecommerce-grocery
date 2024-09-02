package com.grocery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.modal.Address;
import com.grocery.modal.User;
import com.grocery.repository.AddressRepository;
import com.grocery.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

    
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
		super();
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	String s = "Address not found";

    @Transactional
    public Address saveAddress(Long userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        address.setUser(user);
        return addressRepository.save(address);
    }
    
    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }
    
    @Transactional
    public List<Address> setDefaultAddress(Long userId, Long addressId) {
        addressRepository.updateDefaultAddressToFalse(userId);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException(s));
        address.setDefault_address(true);
        addressRepository.save(address);
        return addressRepository.findByUserId(userId);
    }
    
    @Transactional
    public Address updateAddress(Long userId, Long addressId, Address updatedAddress) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException(s));
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to the user");
        }

        address.setAddress_line1(updatedAddress.getAddress_line1());
        address.setAddress_line2(updatedAddress.getAddress_line2());
        address.setCountry(updatedAddress.getCountry());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setZipcode(updatedAddress.getZipcode());

        return addressRepository.save(address);
    }

    public void deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException(s));
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to the user");
        }

        addressRepository.delete(address);
    }
    
    public Address findDefaultAddressByUserId(Long userId) {
        return addressRepository.findByUserIdAndDefaultAddressTrue(userId);
    }
}

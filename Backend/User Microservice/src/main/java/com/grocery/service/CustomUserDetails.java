package com.grocery.service;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grocery.domain.USER_ROLE;
import com.grocery.modal.User;
import com.grocery.repository.UserRepository;
 
@Service
public class CustomUserDetails implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public CustomUserDetails(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not found with email " + username);
		}
		
		USER_ROLE role = user.getRole();
		
		if(role==null) role = USER_ROLE.ROLE_CUSTOMER;
		
		System.out.println("role  ---- "+role);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
 
}

